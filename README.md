## mp4-analyzer

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
        "type": "moof",
        "subBoxes": [
            {
                "size": 16,
                "type": "mfhd",
                "subBoxes": []
            },
            {
                "size": 157,
                "type": "traf",
                "subBoxes": [
                    {
                        "size": 24,
                        "type": "tfhd",
                        "subBoxes": []
                    },
                    {
                        "size": 20,
                        "type": "trun",
                        "subBoxes": []
                    },
                    {
                        "size": 44,
                        "type": "uuid",
                        "subBoxes": []
                    },
                    {
                        "size": 61,
                        "type": "uuid",
                        "subBoxes": []
                    }
                ]
            }
        ]
    },
    {
        "size": 17908,
        "type": "mdat",
        "subBoxes": []
    }
]
```
### Getting Started
These instructions will help you get a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites
Before you begin, make sure you have the following software installed on your machine:

- Java 11 or later
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

### Running the Application

2 - Open your web browser and navigate to `http://localhost:8080/analyze?url=<url>` where `<url>` is the URL of the MPEG-4 Part 12 file you want to analyze. 

### Built With
- **Spring Boot** - The web framework used
- **Reactor** - The reactive programming library used
- **Jackson** - The JSON parsing library used