# GitHub Upload Checklist

Use this before making the repository public.

## Commit these

- Android source code under `app/`
- TFLite model assets under `app/src/main/assets/` if you are allowed to distribute them
- `README.md`
- `docs/DATASET.md`
- `docs/ARCHITECTURE.md`
- `docs/model-training/YOLOv5_training_summary.md`
- `docs/model-training/YOLOv5_Colab_training_redacted.pdf`
- `notebooks/yolov5_training_clean.ipynb`
- `app/google-services.example.json`

## Do not commit these

- Raw dataset zips or image folders
- Real `app/google-services.json`
- Raw Colab PDFs/notebooks with API keys
- Private server IPs or cloud credentials
- Android signing keys: `.jks`, `.keystore`
- `local.properties`, `.gradle/`, `build/`

## Suggested Git commands

```bash
git init
git add .
git commit -m "Initial public portfolio version of SafeEat"
git branch -M main
git remote add origin <your-github-repo-url>
git push -u origin main
```

## Suggested repository description

AI-powered Android app for fruit freshness detection using Kotlin, Firebase, Retrofit, YOLOv5, and TensorFlow Lite.


## Quick final check

- Open `notebooks/yolov5_training_clean.ipynb` on GitHub/Colab to confirm it renders correctly.
- Confirm no private keys, private server IPs, Google Drive file IDs, or real Firebase config files are committed.
