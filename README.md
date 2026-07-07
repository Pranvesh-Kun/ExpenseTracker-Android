# 💰 Expense Tracker
A modern Android expense tracker built with Kotlin and Jetpack Compose, featuring SMS-based expense import, Excel export, monthly analytics, and on-device machine learning for automatic expense categorization.


![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)
![Room](https://img.shields.io/badge/Room-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Hilt](https://img.shields.io/badge/Hilt-4285F4?style=for-the-badge)
![ONNX Runtime](https://img.shields.io/badge/ONNX_Runtime-005CED?style=for-the-badge)
![Scikit--Learn](https://img.shields.io/badge/Scikit--Learn-F7931E?style=for-the-badge&logo=scikitlearn&logoColor=white)

## 📱 Demo

<p align="center">
  <img src="demo/demo.gif" width="300"/>
</p>

## ✨ Features

- 💸 **Expense Management** – Add, edit, delete, and organize daily expenses with an intuitive interface.
- 🔍 **Search & Filtering** – Quickly find expenses using merchant name and category-based filters.
- 📊 **Monthly Analytics** – Visualize spending patterns with monthly summaries and category-wise insights.
- 📥 **SMS Transaction Import** – Automatically extract transaction details from bank SMS messages to reduce manual entry.
- 🧠 **On-device ML Categorization** – Predicts expense categories from merchant names using a lightweight TF-IDF + Naive Bayes model running entirely offline with ONNX Runtime.
- 📄 **Excel Export** – Export all recorded expenses to a formatted `.xlsx` spreadsheet for reporting and backup.
- 🏗️ **Modern Android Architecture** – Built using MVVM, Room, Hilt, and Jetpack Compose following Android best practices.

## 🛠️ Tech Stack

### Android Development
- **Kotlin** – Primary programming language
- **Jetpack Compose** – Declarative UI toolkit
- **MVVM Architecture** – Separation of UI and business logic
- **Room Database** – Local data persistence
- **Hilt** – Dependency Injection
- **Material 3** – Modern Android UI components

### Machine Learning
- **Scikit-learn** – Model training
- **TF-IDF Character N-grams** – Merchant name feature extraction
- **Multinomial Naive Bayes** – Expense category prediction
- **ONNX** – Model export format
- **ONNX Runtime** – On-device inference

### Data Processing
- **Apache POI** – Excel (.xlsx) export
- **Android SMS Content Provider** – Transaction import from SMS

## 🏗️ Architecture

```text
                   User Interface
               (Jetpack Compose UI)
                        │
                        ▼
                  ViewModel (MVVM)
                        │
                        ▼
                   Repository Layer
                  ↙                ↘
                 ▼                  ▼
         Room Database      ONNX ML Classifier
             │                    │
             ▼                    ▼
      Expense Storage      Category Prediction

                 ▲
                 │
      SMS Import / Excel Export
```
## 🧠 Machine Learning Pipeline

The application uses an on-device machine learning model to automatically predict expense categories from merchant names, eliminating the need for manual categorization while keeping all inference completely offline.

### Pipeline

```text
Merchant Name
      │
      ▼
TF-IDF Character N-grams
      │
      ▼
Multinomial Naive Bayes
      │
      ▼
ONNX Export
      │
      ▼
ONNX Runtime (Android)
      │
      ▼
Predicted Expense Category
```

## 🧠 On-device ML Categorization

The application automatically predicts expense categories from merchant names using a lightweight on-device machine learning model, eliminating manual categorization while keeping all predictions offline.

| Property | Value |
|----------|-------|
| Algorithm | Multinomial Naive Bayes |
| Feature Extraction | TF-IDF Character N-grams |
| Dataset | ~800 labeled merchant names |
| Categories | 7 |
| Accuracy | **84.9%** |
| Deployment | ONNX Runtime |

## 📂 Project Structure

```text
ExpenseTracker/
│
├── app/                            // Android application source code
│   ├── src/main/java/              // Kotlin source files
│   ├── src/main/res/               // UI resources
│   └── src/main/assets/            // ONNX model
│
├── ml/
│   ├── dataset/                    // Training dataset
│   ├── train_model.py              // Model training script
│   ├── expense_classifier.onnx
│   └── requirements.txt
│
└── README.md
```
## 🚀 Getting Started

### Prerequisites

- Android Studio
- Android SDK 26+
- JDK 17

### Installation

1. Clone the repository

```bash
git clone https://github.com/<your-username>/ExpenseTracker.git
```

2. Open the project in Android Studio.

3. Sync Gradle dependencies.

4. Build and run the application on an emulator or physical device.

## 🤝 Contributing

Contributions, suggestions, and bug reports are welcome. Feel free to fork the repository, open an issue, or submit a pull request.

