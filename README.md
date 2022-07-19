<p align="center">
  <img src="https://github.com/AfricanBongo/ThousandMiles/blob/master/screenshots/Logo.png" width="240" height="240"/>
</p>

</br>

# 1000 Miles (Mock project)
### :soccer: Goal
The goal of the project was to incentivize citizens for using their personal vehicles less and walking more so as to reduce total fuel import cost for the government.
This would also improve the fitness standard of the general populace and could alternatively be used by medical aid providers to provide benefits for customers who have
higher fitness levels as those people are less prone to succumbing to ailments.

### The dream team:
- Tanaka Mawunga - Project Manager
- Lindokuhle Madlela - Systems analyst
- Tanatswa Mawunga - Tech lead
- Myself - UX Engineer and application architect
- Nkosinathi Khumalo - UI Designer
- Gugulethu Mafu - Senior business analyst
- Byron Mutimusakwa - Product owner
- Paul Njini - Quality Assurance Engineer
- Brandon Ngwenya - Software developer

### What I did
- Designed the user interface and communicated changes and decisions to the rest of the team.
- Translated the user interface designs into the frontend code, i.e. I coded the entire user interface using Jetpack Compose (Android UI framework).
- Produce UML diagrams such as Class and Sequence diagrams that assist in visualizing the architecture of the application, as well as selecting the appropriate technologies for the project.
- Connected the frontend to a Firebase backend for authentication and storing user data, and Health Connect as a data source for steps data.

</br>

## The app at work :wrench:
### 1. Authentication

</br>

| Sign In screen | Sign Up screen |
| --- | --- |
| <img src="https://github.com/AfricanBongo/ThousandMiles/blob/master/screenshots/sign_in.png" width="180" height="400" alt="Sign in screen image"/> | <img src="https://github.com/AfricanBongo/ThousandMiles/blob/master/screenshots/sign_up.png" width="180" height="400" alt="Sign up screen image"/> |

</br>

- Authentication is handled using Firebase Auth service for email and password but Google Account Authentication was also considered but dismissed due to time crunches.
- Error handling was also utilized when fields are left blank, there is no internet connection or the email provided is already associated with another account.

</br>

### 2. Gathering user information

</br>

<img src="https://github.com/AfricanBongo/ThousandMiles/blob/master/screenshots/onboarding.png" width="837" height="500"/>

</br>

- The data gathered from this process is saved to the Realtime Database provided by Firebase.
- The same data will be used to simulate the calculate the amount the user has reduced in carbon emissions.

</br>

### 3. Overview of user's data

</br>

<img src="https://github.com/AfricanBongo/ThousandMiles/blob/master/screenshots/overview.png" width="240" height="533" alt="Overview screen image"/>

#### From top to bottom:
- The user's profile photo and name are displayed as per persisted data in Firebase.
- The overall discount is displayed, as well as an overview of the offers are provided, the user can opt to claim discount by clicking the respective button.
- An overview of the steps data, including the distance covered and the time that they have been actively walking.
- Summary of reduced carbon emission mass, their daily average and daily population average.

#### Additions:
- The discount was calculated using the distance covered by walking against the emission mass that would have been produced had they had been using their personal vehicle instead.
- The reduced carbon emission mass was also calculated using the user's covered distanced.

</br>

## Tools & technologies used
### Design
- Figma -> UI/UX design.
- Lucidchart -> UML design.

### Development
- Firebase -> Authentication and cloud storage.
- Jetpack Compose -> Frontend implementation of the Android application.
- Health Connect API -> Obtain user steps data, distance covered and active time.
- MVVM architecture -> Communicate changes in the model layer efficiently to the UI layer.

</br>

#### Documented and compiled by
#### ``` Donell Mtabvuri ```
