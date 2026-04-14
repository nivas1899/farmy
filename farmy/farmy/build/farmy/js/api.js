/**
 * FARMY API Utilities
 * Centralized API communication module
 */

const API_BASE_URL = '/farmy'; // Adjust based on Tomcat context path

/**
 * Generic fetch wrapper with error handling
 */
async function apiFetch(endpoint, options = {}) {
    const url = `${API_BASE_URL}${endpoint}`;

    const defaultOptions = {
        headers: {
            'Content-Type': 'application/json',
        },
        credentials: 'include', // Include cookies for session
        ...options
    };

    try {
        const response = await fetch(url, defaultOptions);

        // Handle non-JSON responses
        const contentType = response.headers.get('content-type');
        if (!contentType || !contentType.includes('application/json')) {
            throw new Error('Server returned non-JSON response');
        }

        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.message || `HTTP error! status: ${response.status}`);
        }

        return data;

    } catch (error) {
        console.error(`API Error (${endpoint}):`, error);
        throw error;
    }
}

/**
 * Authentication API
 */
const AuthAPI = {
    async login(username, password) {
        return apiFetch('/login', {
            method: 'POST',
            body: JSON.stringify({ username, password })
        });
    },

    async register(username, password) {
        return apiFetch('/register', {
            method: 'POST',
            body: JSON.stringify({ username, password })
        });
    }
};

/**
 * Animal API
 */
const AnimalAPI = {
    async getAll() {
        return apiFetch('/animals');
    },

    async add(animalData) {
        return apiFetch('/animals', {
            method: 'POST',
            body: JSON.stringify(animalData)
        });
    },

    async updateStatus(animalId, status) {
        return apiFetch(`/animals?id=${animalId}&status=${status}`, {
            method: 'PUT'
        });
    },

    async delete(animalId) {
        return apiFetch(`/animals?id=${animalId}`, {
            method: 'DELETE'
        });
    }
};

/**
 * Vaccination API
 */
const VaccinationAPI = {
    async getAll() {
        return apiFetch('/vaccinations');
    },

    async getPending() {
        return apiFetch('/vaccinations?filter=pending');
    },

    async add(vaccinationData) {
        return apiFetch('/vaccinations', {
            method: 'POST',
            body: JSON.stringify(vaccinationData)
        });
    },

    async markDone(vaccineId) {
        return apiFetch(`/vaccinations?id=${vaccineId}&status=Done`, {
            method: 'PUT'
        });
    },

    async delete(vaccineId) {
        return apiFetch(`/vaccinations?id=${vaccineId}`, {
            method: 'DELETE'
        });
    }
};

/**
 * Visitor API
 */
const VisitorAPI = {
    async getAll() {
        return apiFetch('/visitors');
    },

    async getToday() {
        return apiFetch('/visitors?filter=today');
    },

    async add(visitorData) {
        return apiFetch('/visitors', {
            method: 'POST',
            body: JSON.stringify(visitorData)
        });
    }
};

/**
 * Dashboard API
 */
const DashboardAPI = {
    async getMetrics() {
        return apiFetch('/dashboard');
    }
};

/**
 * Toast Notification Helper
 */
function showToast(message, type = 'info') {
    const toast = document.createElement('div');
    toast.className = `toast toast-${type}`;
    toast.textContent = message;
    toast.style.cssText = `
    position: fixed;
    top: 20px;
    right: 20px;
    background: ${type === 'success' ? '#2e7d32' : type === 'error' ? '#d32f2f' : '#1976d2'};
    color: white;
    padding: 16px 24px;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0,0,0,0.2);
    z-index: 10000;
    animation: slideIn 0.3s ease;
  `;

    document.body.appendChild(toast);

    setTimeout(() => {
        toast.style.animation = 'slideOut 0.3s ease';
        setTimeout(() => toast.remove(), 300);
    }, 3000);
}

// Add animation styles
if (!document.getElementById('toast-animations')) {
    const style = document.createElement('style');
    style.id = 'toast-animations';
    style.textContent = `
    @keyframes slideIn {
      from { transform: translateX(400px); opacity: 0; }
      to { transform: translateX(0); opacity: 1; }
    }
    @keyframes slideOut {
      from { transform: translateX(0); opacity: 1; }
      to { transform: translateX(400px); opacity: 0; }
    }
  `;
    document.head.appendChild(style);
}

/**
 * Format date helper
 */
function formatDate(dateString) {
    if (!dateString) return 'N/A';
    const date = new Date(dateString);
    return date.toLocaleDateString('en-US', {
        year: 'numeric',
        month: 'short',
        day: 'numeric'
    });
}

/**
 * Format datetime helper
 */
function formatDateTime(dateString) {
    if (!dateString) return 'N/A';
    const date = new Date(dateString);
    return date.toLocaleString('en-US', {
        year: 'numeric',
        month: 'short',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    });
}
