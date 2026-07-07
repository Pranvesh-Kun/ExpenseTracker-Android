import pandas as pd
from sklearn.naive_bayes import MultinomialNB
from sklearn.feature_extraction.text import TfidfVectorizer 
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score
from sklearn.metrics import confusion_matrix, ConfusionMatrixDisplay
import matplotlib.pyplot as plt
from skl2onnx import convert_sklearn
from skl2onnx.common.data_types import StringTensorType
from sklearn.pipeline import Pipeline

df = pd.read_csv("indian_merchant_category.csv")

X = df["merchant"]
y = df["category"]

X_train, X_test, y_train, y_test = train_test_split(
    X,
    y,
    test_size=0.2,
    random_state=42
)
pipeline = Pipeline([
    ("tfidf", TfidfVectorizer(
        analyzer="char",
        ngram_range=(2,5),
        lowercase=True
    )),
    ("classifier", MultinomialNB(alpha=0.1))
  ]
)

pipeline.fit(X_train, y_train)

y_pred = pipeline.predict(X_test)

accuracy = accuracy_score(y_test, y_pred)

print("Accuracy =", accuracy)

tests = [
    "SWIG",
    "Swiggy Instamart",
    "Swiggyzzz",
    "ZOMTO",
    "Uber",
    "Uber Cab",
    "Rapido",
    "Amazon",
    "Amazon Pay",
    "Amazn",
    "Flipkart",
    "Flipkart Online",
    "Apollo Pharmacy",
    "Netmeds",
    "Burger King",
    "KFC",
    "Keventers",
    "BookMyShow",
    "Physics Wallah",
    "Coursera",
    "Byju's",
    "NPTEL",
    "BESCOM",
    "Tata Power",
    "Random Merchant",
    "Swetha",
    "Tony Store",
    "asdfgh"
]

THRESHOLD = 0.6

for merchant in tests:
    probs = pipeline.predict_proba([merchant])[0]

    bestIndex = probs.argmax()
    confidence = probs[bestIndex]

    if confidence < THRESHOLD:
        predicted = "Unknown"

    classifier = pipeline.named_steps["classifier"]

initial_type = [
    ("merchant", StringTensorType([None, 1]))
]

onnx_model = convert_sklearn(
    pipeline,
    initial_types=initial_type
)

with open("expense_classifier.onnx", "wb") as f:
    f.write(onnx_model.SerializeToString())