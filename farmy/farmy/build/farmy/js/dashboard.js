/**
 * FARMY Dashboard
 * Real-time farm metrics and visualization
 */

// Initialize on page load
document.addEventListener('DOMContentLoaded', function () {
    loadDashboardMetrics();
    initializeCharts();

    // Auto-refresh every 30 seconds
    setInterval(loadDashboardMetrics, 30000);
});

/**
 * Load dashboard metrics from API
 */
async function loadDashboardMetrics() {
    try {
        const metrics = await DashboardAPI.getMetrics();

        // Update summary cards
        updateSummaryCards(metrics);

        // Update charts
        updateCharts(metrics);

        // Update alert box
        updateAlertBox(metrics);

    } catch (error) {
        console.error('Error loading dashboard:', error);
        showToast('Failed to load dashboard data', 'error');
    }
}

/**
 * Update summary cards
 */
function updateSummaryCards(metrics) {
    const cardData = [
        { id: 'total-animals', value: metrics.totalAnimals || 0 },
        { id: 'sick-animals', value: metrics.sickCount || 0 },
        { id: 'vaccinated-animals', value: metrics.normalCount || 0 },
        { id: 'visitors-today', value: metrics.visitorsToday || 0 }
    ];

    cardData.forEach(card => {
        const element = document.getElementById(card.id);
        if (element) {
            element.textContent = card.value;
            element.classList.add('fade-in');
        }
    });
}

/**
 * Update charts with new data
 */
function updateCharts(metrics) {
    // Update pie chart
    if (window.animalStatusChart) {
        window.animalStatusChart.data.datasets[0].data = [
            metrics.normalCount || 0,
            metrics.sickCount || 0,
            metrics.isolatedCount || 0,
            metrics.deadCount || 0
        ];
        window.animalStatusChart.update();
    }
}

/**
 * Update alert box
 */
function updateAlertBox(metrics) {
    const alertBox = document.getElementById('alert-box');
    if (!alertBox) return;

    const alerts = [];

    if (metrics.sickCount > 0) {
        alerts.push(`⚠️ ${metrics.sickCount} animals marked sick`);
    }

    if (metrics.pendingVaccinations > 0) {
        alerts.push(`⏰ ${metrics.pendingVaccinations} vaccination(s) pending`);
    }

    if (alerts.length > 0) {
        alertBox.innerHTML = alerts.join(' &nbsp; | &nbsp; ');
        alertBox.style.display = 'block';
    } else {
        alertBox.innerHTML = '✅ All systems normal';
        alertBox.style.background = '#d4edda';
        alertBox.style.borderColor = '#28a745';
        alertBox.style.color = '#155724';
    }
}

/**
 * Initialize Chart.js charts
 */
function initializeCharts() {
    // Pie chart for animal health status
    const pieCtx = document.getElementById('animalStatusChart');
    if (pieCtx) {
        window.animalStatusChart = new Chart(pieCtx, {
            type: 'doughnut',
            data: {
                labels: ['Normal', 'Sick', 'Isolated', 'Dead'],
                datasets: [{
                    data: [0, 0, 0, 0],
                    backgroundColor: [
                        '#2e7d32', // Normal - green
                        '#d32f2f', // Sick - red
                        '#f57c00', // Isolated - orange
                        '#616161'  // Dead - gray
                    ],
                    borderWidth: 2,
                    borderColor: '#ffffff'
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        position: 'bottom',
                        labels: {
                            padding: 15,
                            font: {
                                size: 12
                            }
                        }
                    },
                    title: {
                        display: true,
                        text: 'Animal Health Distribution',
                        font: {
                            size: 16,
                            weight: 'bold'
                        },
                        padding: 20
                    }
                }
            }
        });
    }
}
