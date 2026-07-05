# Notebooks

This folder contains a public-safe Colab notebook template for the YOLOv5 training workflow.

## Files

- `yolov5_training_clean.ipynb` — cleaned notebook for dataset download, filtering/relabeling, YOLOv5 training, validation, and TFLite export. It uses runtime secrets/placeholders instead of hard-coded API keys, Google Drive IDs, private URLs, or server IP addresses.

## Before publishing updates

Do not commit raw notebooks that contain Roboflow API keys, Google Drive file IDs, private Colab URLs, personal filesystem paths, or private model-server addresses. Clear outputs if they contain sensitive values.
