# PopularMovies
Popular Movies - This is the repository for Stage 1 of the Udacity Android Associate Developer Nanodegree

## Getting Started

Popular Movies provides an interface for the TMDB movie API. Note: This Android application supports both tablets and phones. Information presented is consumeed via the TMDB API and presented in a simple two screen UX.

Popular Movies Image

Tablet Version (Pixel C)

![Popular Movies tablet application](screenshots/pixelc_screenshot_med.png?raw=true "Stage 1")

Tablet Version (Nexus 9)

Landscape Mode - Main Screen
![Popular Movies tablet application](screenshots/nexus9_screenshot0_med.png?raw=true "Stage 1")

Landscape Mode - Detail Screen
![Popular Movies tablet application](screenshots/nexus9_screenshot1_med.png?raw=true "Stage 1")

Portrait Mode - Main Screen
![Popular Movies tablet application](screenshots/nexus9_screenshot2_med.png?raw=true "Stage 1")

Portrait Mode - Detail Screen
![Popular Movies tablet application](screenshots/nexus9_screenshot3_med.png?raw=true "Stage 1")

Phone Version (Pixel XL)

![Popular Movies phone application](screenshots/pixelxl_main_med.png?raw=true "Stage 1")

![Popular Movies phone application](screenshots/pixelxl_detail_med.png?raw=true "Stage 1")


## Application architecture (generated using the quickwindiagram tool)

Libraries
  +Picasso
  +Volley

Note:
  Volley has now been moved to the following location: https://github.com/google/volley

### Prerequisites

The project requires the Volley library and a valid TMDB API key to compile and run. A valid TMDB API key can be accessed via the TMDB website.

The Volley library has been cloned into the P1_AAD project.

i.e.

+ Parent directory
  + P1_AAD
    + app
    + build
    + gradle
    + screenshots
    + volley


### How to build

1. git clone https://github.com/rosera/P1_AAD.git
2. cd P1_AAD
3. git clone https://github.com/google/volley.git (i.e. clone into the empty Volley directory)
4. Start Android Studio
5. Import the P1_AAD project
6. Create/Amend a new gradle file - gradle.properties
7. Edit gradle.properties and add TMDB_API_KEY="Enter Your valid API KEY" (Note: if you dont already have a valid TMDB API key, sign up at https://developers.themoviedb.org/3/getting-started).
8. Compile and run the code

## Acknowledgments

* Massive thanks to the Udacity coaches and fellow students for the help and support
* Fellow students on AAD for keeping me motivated
