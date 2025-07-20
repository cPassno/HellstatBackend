const chartCanvas = document.getElementById('factionChart');
const loadBtn = document.getElementById('loadDataBtn');
let chartInstance = null;

function fetchFactionData(startDate, endDate) {
    const url = `/api/hellstat/factionStats?start=${startDate}&end=${endDate}`;
    return fetch(url).then(res => res.json());
}

function getSelectedMetric() {
    const selected = document.querySelector('input[name="metric"]:checked');
    return selected ? selected.value : null;
}

function getFactionColor(faction) {
    if (faction === 'Terminid') return 'rgba(255, 206, 86, 0.9)'; // yellow
    if (faction === 'Automaton') return 'rgba(255, 99, 132, 0.9)'; // red
    if (faction === 'Illuminate') return 'rgba(153, 102, 255, 0.9)'; // purple
    return 'rgba(200, 200, 200, 0.6)';
}

function transformFactionData(data, metric) {
    const timestamps = Array.from(new Set(data.map(d => d.timestamp))).sort();
    const factions = ['Terminid', 'Illuminate', 'Automaton'];
    const dataMap = {};

    data.forEach(d => {
        const key = `${d.timestamp}|${d.factionName}`;
        dataMap[key] = d;
    });

    const datasets = factions.map(faction => {
        const values = timestamps.map(ts => {
            const record = dataMap[`${ts}|${faction}`];
            return record ? record[metric] : null;
        });
        return {
            label: faction,
            data: values,
            borderColor: getFactionColor(faction),
            backgroundColor: getFactionColor(faction),
            fill: false,
            tension: 0.3,
            borderWidth: 2,
            pointRadius: 0,
            pointHoverRadius: 4,
            yAxisID: 'y'
        };
    });

    return {
        labels: timestamps.map(ts => ts.slice(0, 16).replace('T', ' ')),
        datasets
    };
}

function renderChart(data, selectedMetric) {
    const labels = data.labels;

    let yAxisConfig = {
        type: 'linear',
        position: 'left',
        title: {
            display: true,
            text: '',
            color: '#ffffff'
        },
        ticks: {
            color: '#ffffff'
        },
        grid: {
            drawOnChartArea: true,
            color: '#777'
        },
        min: undefined,
        max: undefined
    };

    if (selectedMetric === 'missionSuccessRate') {
        //yAxisConfig.min = 70;
        //yAxisConfig.max = 95;
        yAxisConfig.title.text = 'Mission Success Rate (%)';
    } else if (selectedMetric === 'kdr') {
        //yAxisConfig.min = 0;
        //yAxisConfig.max = 70;
        yAxisConfig.title.text = 'Kill/Death Ratio';
    } else if (selectedMetric === 'players') {
        yAxisConfig.title.text = 'Players';
    } else {
        yAxisConfig.title.text = selectedMetric.charAt(0).toUpperCase() + selectedMetric.slice(1);
    }

    const scaleConfig = {
        y: yAxisConfig,
        x: {
            ticks: {
                color: '#777'
            },
            grid: {
                color: '#777'
            }
        }
    };

    const ctx = chartCanvas.getContext('2d');
    if (chartInstance) chartInstance.destroy();

    chartInstance = new Chart(ctx, {
        type: 'line',
        data: { labels, datasets: data.datasets },
        options: {
            scales: scaleConfig,
            plugins: {
                legend: {
                    position: 'top',
                    labels: {
                        color: '#ffffff'
                    }
                },
                tooltip: {
                    titleColor: '#ffffff',
                    bodyColor: '#ffffff',
                    backgroundColor: '#222222'
                }
            },
            interaction: {
                mode: 'nearest',
                intersect: false
            }
        }
    });
}

loadBtn.addEventListener('click', () => {
    const start = document.getElementById('startDate').value;
    const end = document.getElementById('endDate').value;
    const metric = getSelectedMetric();

    if (!start || !end || !metric) {
        alert("Please select start date, end date, and a metric.");
        return;
    }

    fetchFactionData(start, end)
        .then(data => {
            if (!data || data.length === 0) {
                alert("No data found for this range.");
                return;
            }
            const transformed = transformFactionData(data, metric);
            renderChart(transformed, metric);
        })
        .catch(err => {
            console.error("Failed to load faction stats:", err);
            alert("Error loading data.");
        });
});
