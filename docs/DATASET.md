# Dataset Sources and Attribution

This repository does not include the raw image dataset. For a public GitHub portfolio repository, the safer and cleaner approach is to cite the dataset sources and include only the app, model assets, training documentation, and reproduction notes.

## Main uploaded dataset reference

The uploaded dataset archive `Fruits_Detection_and_Quality_Analysis.v9i.zip` is a YOLOv5-format Roboflow export:

- Dataset: **Fruits Detection and Quality Analysis**
- Roboflow workspace: `jawaharlal-nehru-university-utour`
- Roboflow project: `fruits-detection-and-quality-analysis`
- Version: `9`
- License: **CC BY 4.0**
- Export format: YOLOv5 PyTorch
- Classes in original export: 29 fruit-quality classes including apple, banana, mango, melon, orange, peach, and pear freshness/rottenness variants
- Roboflow URL: `https://universe.roboflow.com/jawaharlal-nehru-university-utour/fruits-detection-and-quality-analysis/dataset/9`

## Additional Roboflow sources referenced in the Colab training PDF

The Colab export shows a combined-dataset workflow using multiple Roboflow projects. The notebook downloaded and filtered/relabelled classes from these sources:

| Workspace | Project | Version | Notes |
|---|---|---:|---|
| `nivin-a` | `fruits-hnsor` | 3 | Source used for fruit classes before filtering/relabeling |
| `applesbob` | `applebapples` | 2 | Apple freshness/rottenness source |
| `jawaharlal-nehru-university-utour` | `fruits-detection-and-quality-analysis` | 9 | Multi-fruit quality dataset, CC BY 4.0 in uploaded archive |
| `ahmd5448` | `rotten-banana-wjjw1` | 3 | Banana quality source |
| `maryam-saidbauwain-pxj1l` | `banana-hwclp` | 1 | Banana source |
| `object-detection-fmrts` | `fresh-rotten-oranges` | 3 | Orange freshness/rottenness source |
| `neha-chandekar-yxsnl` | `fresh-rotten-orange-classifier` | 2 | Orange freshness/rottenness source |
| `fruit-ripening` | `banana-ripening-process` | 2 | Banana ripening source |

## How to describe this in a README, CV, or application

Suggested wording:

> Dataset: Public fruit-quality datasets from Roboflow Universe were used for model experimentation. The main referenced dataset is Fruits Detection and Quality Analysis v9, licensed under CC BY 4.0. Raw images are not redistributed in this repository; dataset sources and attribution are documented.

## Why the dataset zip is not committed

- It is larger than the application source and would make the repository harder to review.
- The raw data belongs to external dataset providers and should be redistributed only according to each dataset's license.
- For PhD/job applications, reviewers usually prefer a clear dataset citation, training pipeline, and demo-ready code over a large dataset dump.

## Reproduction notes

To reproduce training, download the datasets from Roboflow under their respective licenses, filter/relabel classes as shown in the redacted Colab PDF, split the combined dataset into train/validation/test sets, train YOLOv5, and export the trained weights to TFLite.
