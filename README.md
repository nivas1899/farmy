# 🚜 Farmy: Smart Farm Management System

[![Python](https://img.shields.io/badge/Python-3.9-blue?style=flat&logo=python)](https://www.python.org/downloads/)
[![Flask](https://img.shields.io/badge/Flask-Framework-red?style=flat&logo=flask)](https://flask.palletsprojects.com/)
[![MySQL](https://img.shields.io/badge/MySQL-Database-4479A1?style=flat&logo=mysql)](https://www.mysql.com/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

**Farmy** is a professional-grade Farm Management System designed for modern agriculture. It provides a comprehensive suite of tools to manage livestock, monitor vaccinations, track visitors, and generate detailed reports for data-driven farming.

---

## 🌟 Key Features

- **🐄 Livestock Management**: Track animal health, inventory, and status.
- **💉 Vaccination Scheduler**: Automated tracking of vaccination cycles for livestock.
- **👤 Visitor Logs**: Monitor and record on-farm visitors for security and compliance.
- **📊 Reporting Dashboard**: Visualize farm productivity and health metrics.
- **🛡️ Secure Database**: Structured PostgreSQL/MySQL backend for reliable data persistence.

## 🛠️ Tech Stack

- **Backend**: Flask (Python) with RESTful API architecture.
- **Frontend**: Responsive HTML5, CSS3, and JavaScript (Vite-optimized).
- **Database**: SQL-based storage with pre-configured migration scripts.
- **Automation**: Shell scripts for rapid database setup and deployment.

## 📂 Project Structure

```text
.
├── backend/           # Flask API and server logic
├── frontend/          # Web dashboard (HTML, CSS, JS)
├── database/          # SQL schemas and setup scripts
└── scripts/           # Deployment and automation tools
```

## 📖 Quick Start

1. **Clone the repository:**
   ```bash
   git clone https://github.com/nivas1899/farmy.git
   cd farmy
   ```
2. **Setup Database**:
   ```bash
   ./setup_database.sh
   ```
3. **Run Backend**:
   ```bash
   cd backend && pip install -r requirements.txt && python app.py
   ```

---
Created with ❤️ by [nivas1899](https://github.com/nivas1899)
