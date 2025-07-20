const planetSelect = document.getElementById('planetSelect');
const loadBtn = document.getElementById('loadDataBtn');
const chartCanvas = document.getElementById('planetChart');
const metricCheckboxes = document.querySelectorAll('input[type=checkbox]');
let chartInstance = null;

// Load planet list
function populatePlanetDropdown() {
    fetch('/api/hellstat/planets')
        .then(res => res.json())
        .then(planets => {
            planetSelect.innerHTML = '';
            planets.forEach(planet => {
                const opt = document.createElement('option');
                opt.value = planet.planetIndex;
                opt.textContent = planet.name;
                planetSelect.appendChild(opt);
            });
        })
        .catch(err => {
            console.error('Planet list error:', err);
            planetSelect.innerHTML = '<option disabled>Error loading</option>';
        });
}

function getSelectedMetrics() {
    return Array.from(metricCheckboxes)
        .filter(cb => cb.checked)
        .map(cb => cb.value);
}

function fetchPlanetData(planetIndex, startDate, endDate) {
    const url = `/api/hellstat/planetStats?planetIndex=${planetIndex}&start=${startDate}&end=${endDate}`;
    return fetch(url).then(res => res.json());
}

function transformData(data, metrics) {
    const labels = data.map(row => row.timestamp.slice(0, 16).replace("T", " "));
    const datasets = metrics.map(metric => ({
        label: metric,
        data: data.map(row => row[metric]),
        fill: false,
        borderColor: getRandomColor(),
        tension: 0.3,
        yAxisID: `y_${metric}`
    }));
    return { labels, datasets };
}

function getRandomColor() {
    const r = () => Math.floor(Math.random() * 200);
    return `rgba(${r()},${r()},${r()},0.8)`;
}

function renderChart(data) {
    const labels = data.labels;
    const datasets = data.datasets.map((dataset, i) => ({
        ...dataset,
        borderColor: dataset.borderColor || COLORS[i % COLORS.length],
        backgroundColor: dataset.borderColor || COLORS[i % COLORS.length],
        fill: false,
        tension: 0.3,
        borderWidth: 2,
        pointRadius: 0,
        pointHoverRadius: 4,
        yAxisID: i === 0 ? 'y' : `y${i}`,
    }));

    const yScales = {};

    datasets.forEach((dataset, index) => {
        const axisId = dataset.yAxisID;

        let scaleOptions = {
            type: 'linear',
            position: index === 0 ? 'left' : 'right',
            grid: {
                color: '#777',
                drawOnChartArea: index === 0
            },
            title: {
                display: true,
                text: dataset.label,
                color: '#ffffff',
                font: { size: 14, weight: 'bold' }
            },
            ticks: {
                color: '#ffffff'
            }
        };

        if (['kdr', 'missionSuccessRate'].includes(dataset.label)) {
            scaleOptions.min = 70;
            scaleOptions.max = 95;
        }

        yScales[axisId] = scaleOptions;
    });

    const ctx = chartCanvas.getContext('2d');
    if (chartInstance) chartInstance.destroy();

    chartInstance = new Chart(ctx, {
        type: 'line',
        data: {
            labels,
            datasets
        },
        options: {
            responsive: true,
            interaction: {
                mode: 'nearest',
                intersect: false
            },
            plugins: {
                legend: {
                    position: 'top',
                    labels: {
                        font: {
                            size: 14
                        },
                        color: '#ffffff',
                        boxWidth: 15,
                        padding: 20
                    }
                },
                tooltip: {
                    enabled: true,
                    mode: 'nearest',
                    intersect: false,
                    titleColor: '#ffffff',
                    bodyColor: '#ffffff',
                    backgroundColor: '#222',
                    titleFont: { size: 16, weight: 'bold' },
                    bodyFont: { size: 14 },
                    padding: 10
                }
            },
            scales: {
                x: {
                    type: 'category',
                    grid: {
                        color: '#777'
                    },
                    title: {
                        display: true,
                        text: 'Timestamp',
                        color: '#ffffff',
                        font: { size: 14, weight: 'bold' }
                    },
                    ticks: {
                        color: '#777',
                        maxRotation: 45,
                        minRotation: 45,
                        maxTicksLimit: 10
                    }
                },
                ...yScales
            }
        }
    });
}


const COLORS = [
    'rgba(54, 162, 235, 0.8)', // Blue
    'rgba(255, 99, 132, 0.8)', // Red
    'rgba(255, 206, 86, 0.8)', // Yellow
    'rgba(75, 192, 192, 0.8)', // Teal
    'rgba(153, 102, 255, 0.8)', // Purple
];

loadBtn.addEventListener('click', () => {
    const start = document.getElementById('startDate').value;
    const end = document.getElementById('endDate').value;
    const planetIndex = planetSelect.value;
    const metrics = getSelectedMetrics();

    if (!start || !end || !planetIndex || metrics.length === 0) {
        alert("Please select start date, end date, a planet, and at least one metric.");
        return;
    }

    fetchPlanetData(planetIndex, start, end)
        .then(data => {
            if (!data || data.length === 0) {
                alert("No data found for this time range.");
                return;
            }
            const transformed = transformData(data, metrics);
            renderChart(transformed);
        })
        .catch(err => {
            console.error("Failed to load stats:", err);
            alert("Error loading data.");
        });
});

// Initialize
populatePlanetDropdown();
