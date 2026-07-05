# SafeEat - AI Fruit Freshness Detection Android App

SafeEat is an Android/Kotlin portfolio project that demonstrates an end-to-end mobile AI workflow for food-safety assistance. Users can capture or upload a fruit image, send it to a YOLOv5-based inference service, and view the detected fruit freshness result in the app.

This repository is arranged for academic applications, job applications, and technical review. It includes the Android application, trained TensorFlow Lite assets, and documentation for the YOLOv5 training process and dataset provenance.

## Highlights

- Android app built with Kotlin, XML layouts, Firebase Authentication/Firestore, Retrofit, OkHttp, and TensorFlow Lite dependencies.
- Fruit freshness / quality detection workflow using image upload and model inference.
- YOLOv5 training evidence included as a redacted Colab PDF under `docs/model-training/`.
- Dataset provenance documented under `docs/DATASET.md`; the raw dataset is intentionally not committed.
- Public-safe version: Firebase config, Roboflow API key, private Colab URL, Google Drive file ID, and model-server IP are removed or redacted.

## Repository layout

```text
SafeEat/
├── app/                         # Android application source
│   ├── src/main/java/...         # Kotlin activities and app logic
│   ├── src/main/res/...          # XML layouts, drawables, launcher assets
│   └── src/main/assets/          # TFLite model files used by the app
├── docs/
│   ├── DATASET.md                # Dataset sources and attribution
│   ├── ARCHITECTURE.md           # App and ML workflow overview
│   ├── SECURITY_AND_PRIVACY.md   # What was removed/redacted before publishing
│   └── model-training/
│       ├── YOLOv5_training_summary.md
│       └── YOLOv5_Colab_training_redacted.pdf
├── notebooks/
│   ├── README.md                  # Notebook publication notes
│   └── yolov5_training_clean.ipynb # Public-safe YOLOv5 Colab template
├── GITHUB_UPLOAD_CHECKLIST.md
├── build.gradle.kts
├── settings.gradle.kts
└── gradle/
```

## Setup

1. Clone the repository.
2. Open the root folder in Android Studio.
3. Create a Firebase Android app using the package name `com.example.safeeat`.
4. Download your own `google-services.json` from Firebase and place it at `app/google-services.json`. This file is ignored by Git.
5. Configure your inference API endpoint in `app/build.gradle.kts` by replacing the placeholder `MODEL_API_BASE_URL`.
6. Sync Gradle and run the app on an emulator or Android device.

## Model and inference notes

The app contains TFLite assets under `app/src/main/assets/`, while the current classification screen sends the selected image to a backend prediction endpoint through Retrofit. The backend is expected to return JSON containing a base64-encoded annotated image and prediction results.

The public version uses `https://example.com/` as a placeholder endpoint. Replace it with your deployed model service before running inference.

## Dataset attribution

The project uses public Roboflow Universe datasets and a custom combined YOLOv5 training workflow. Dataset details, licenses, and attribution guidance are documented in `docs/DATASET.md`. Raw images and annotations are not included in this repository.

## Training documentation

A redacted copy of the Colab PDF is included in `docs/model-training/YOLOv5_Colab_training_redacted.pdf`. It keeps the training/export evidence while removing publish-sensitive values. A concise written summary is available in `docs/model-training/YOLOv5_training_summary.md`, and a public-safe cleaned notebook template is included at `notebooks/yolov5_training_clean.ipynb`.

## Notes for reviewers

This is a student/research portfolio project. The repository is organized to show mobile development, ML model integration, dataset preparation, and deployment awareness rather than to distribute the raw training dataset.
