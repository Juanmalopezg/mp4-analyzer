[![Java CI](https://github.com/Juanmalopezg/mp4-analyzer/actions/workflows/ci.yml/badge.svg)](https://github.com/Juanmalopezg/mp4-analyzer/actions/workflows/ci.yml) [![Test Coverage](https://raw.githubusercontent.com/Juanmalopezg/mp4-analyzer/gh-pages/badges/jacoco.svg)](https://juanmalopezg.github.io/mp4-analyzer/)

![](src/main/resources/static/boxes.png)
# MPEG-4 Part 12 Analyzer

This project analyzes MPEG-4 Part 12 files and returns a JSON representation of the file structure.

**Request**

```
http://localhost:8080/analyze?url=https://demo.castlabs.com/tmp/text0.mp4
```

**Response**

```
[
    {
        "size": 181,
        "type": "MOOF",
        "subBoxes": [
            {
                "size": 16,
                "type": "MFHD",
                "subBoxes": []
            },
            {
                "size": 157,
                "type": "TRAF",
                "subBoxes": [
                    {
                        "size": 24,
                        "type": "TFHD",
                        "subBoxes": []
                    },
                    {
                        "size": 20,
                        "type": "TRUN",
                        "subBoxes": []
                    },
                    {
                        "size": 44,
                        "type": "UUID",
                        "subBoxes": []
                    },
                    {
                        "size": 61,
                        "type": "UUID",
                        "subBoxes": []
                    }
                ]
            }
        ]
    },
    {
        "size": 17908,
        "type": "MDAT",
        "subBoxes": []
    }
]
```

### Getting Started

To get started with this project, follow the instructions below to set up your development environment:

### Prerequisites

Before you begin, make sure you have the following software installed on your machine:

- Java 17 or later
- Maven

### Installing

1 - Clone the repository:

```
git clone https://github.com/Juanmalopezg/mp4-analyzer.git
```

2 - Navigate to the project directory:

```
cd mp4-analyzer
```

3 - Build the project:

```
mvn clean package
```

### Docker

Alternatively, you can run the application in a Docker container.

1. Build the Docker image:

```
docker build -t mp4-analyzer .
```

2. Run the Docker container:

```
docker run --name mp4-analyzer -d -p 8080:8080 mp4-analyzer
```

The application should now be running on **http://localhost:8080**.

### Running the Application

2 - Open your web browser and navigate to `http://localhost:8080/analyze?url=<url>` where `<url>` is the URL of the
MPEG-4 Part 12 file you want to analyze.

### Built With

- **Spring Boot** - The web framework used
- **Reactor** - The reactive programming library used
- **Jackson** - The JSON parsing library used
