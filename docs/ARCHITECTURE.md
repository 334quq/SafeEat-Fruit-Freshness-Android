# Architecture Overview

## High-level workflow

```text
User image
   ↓
Android app image capture/upload screen
   ↓
Classification activity
   ↓
Retrofit multipart upload to model API
   ↓
YOLOv5 inference service
   ↓
JSON response: annotated image + class/confidence results
   ↓
Result displayed in the Android UI
```

## Android side

- Language: Kotlin
- UI: Android XML layouts, AppCompat activities
- Authentication/database: Firebase Auth and Firestore dependencies
- Networking: Retrofit + Gson converter + OkHttp
- Model assets: TFLite files stored in `app/src/main/assets/`

## ML side

- Base model: YOLOv5 small model family used in Colab training/export workflow
- Input size in training command: 640 px
- Training command shown in the Colab export: YOLOv5 training with custom `data.yaml`, 30 epochs, and `yolov5s.pt` starting weights
- Output: trained model exported to TensorFlow Lite FP16 format for mobile/deployment experiments

## Current integration status

The repository contains TFLite model assets, but the visible app flow currently posts images to a backend inference endpoint. This is a common student-project architecture because it keeps mobile inference lightweight and allows server-side post-processing/visualization. A future improvement would be to add a fully offline TFLite inference path inside the app.
