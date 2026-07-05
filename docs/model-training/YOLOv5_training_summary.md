# YOLOv5 Training Summary

This folder contains a redacted PDF export of the Google Colab training notebook. The raw notebook/PDF should not be published unless all private values are removed.

## What the Colab workflow shows

- Multiple Roboflow datasets were downloaded in YOLOv5 format.
- Dataset folders were filtered, merged, and relabelled into a smaller fruit-quality detection task.
- The combined dataset was split into train/validation/test subsets.
- YOLOv5 was cloned from Ultralytics and trained with a custom `data.yaml`.
- Training used image size 640 and 30 epochs with `yolov5s.pt` as the initial weights.
- The trained weights were exported to TensorFlow SavedModel and TensorFlow Lite.
- The export log shows a successful FP16 TFLite export named `best-fp16.tflite`.

## Evidence included

- `YOLOv5_Colab_training_redacted.pdf`: public-safe copy of the Colab print export.
- `../../notebooks/yolov5_training_clean.ipynb`: cleaned notebook template suitable for GitHub.

## Recommended future improvement

The repository now includes `notebooks/yolov5_training_clean.ipynb`, a cleaned `.ipynb` template with:

1. No API keys.
2. No private Google Drive IDs.
3. Clear markdown explanations for dataset filtering, class mapping, training, validation, and export.
4. Colab setup cells for YOLOv5 and Roboflow.
5. Placeholder cells for metrics, confusion matrix, and example predictions after rerunning training.
