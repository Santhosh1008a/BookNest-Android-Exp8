# Experiment 6 — Basic Android Views

### BookNest — Android Book Discovery Application

This project is a continuation of the BookNest application, developed as part of the MCA Android Development laboratory. While Experiment 5 focused on Android Notifications, **Experiment 6** demonstrates the use of basic Android Views to build interactive screens for the BookNest app.

---

## 1. Experiment Details

*   **Experiment Number:** 6
*   **Experiment Name:** Develop an Android application using basic Views
*   **Application:** BookNest
*   **Language:** Kotlin
*   **Platform:** Android
*   **IDE:** Android Studio
*   **Developer:** Sandy
*   **USN:** 25MCAR0133
*   **Course:** MCA

---

## 2. Aim
To develop an Android application using basic Android Views such as **TextView, EditText, ImageView, Button, CheckBox, RadioButton, Switch, Spinner, RatingBar, and ProgressBar**, and demonstrate their usage in a simple book discovery app.

---

## 3. Objective
*   Understand the purpose and properties of standard Android UI components.
*   Design a premium, interactive user interface using XML layouts.
*   Handle user interactions and inputs using Kotlin.
*   Implement event listeners to respond to user actions in real-time.
*   Manage data passing between Activities using Intent Extras.
*   Demonstrate Activity back-stack management for secure logout functionality.

---

## 4. Scenario
BookNest is a digital sanctuary for book discovery. In this experiment, the basic views are integrated into the core user journey:
1.  **Login**: Users enter their credentials using **EditText**.
2.  **Discovery**: Browse through categories and select books.
3.  **Book Details**: The primary hub for Experiment 6. Users view metadata via **TextViews** and **ImageViews**, rate books using a **RatingBar**, update their reading status with **RadioButtons** and **ProgressBar**, and toggle preferences with **Switches** and **CheckBox**es.
4.  **Interaction**: Users can save their personal notes in an **EditText** and perform actions like "Read Book" or "Save Changes" via **Buttons**.

---

## 5. Basic Views Implementation

| View | Purpose | Implementation Location |
| :--- | :--- | :--- |
| **TextView** | Displays titles, authors, descriptions, and user profile info. | Throughout all screens |
| **ImageView** | Displays book covers and decorative vector icons. | `BookDetailsActivity`, `HomeFragment` |
| **EditText** | Accepts user input for Name, USN, and Personal Notes. | `LoginActivity`, `BookDetailsActivity` |
| **Button** | Triggers login, saving changes, and opening resources. | `LoginActivity`, `BookDetailsActivity` |
| **CheckBox** | Allows users to toggle "Add to Favorites" status. | `BookDetailsActivity` |
| **RadioButton** | Provides mutually exclusive "Reading Status" choices. | `BookDetailsActivity` (within RadioGroup) |
| **Switch** | Toggles Notifications, Dark Mode, and Reminders. | `UserSettingsActivity`, `BookDetailsActivity` |
| **Spinner** | Provides a dropdown menu for selecting book genres. | `BookDetailsActivity` |
| **RatingBar** | Allows users to provide a 1-5 star rating for a book. | `BookDetailsActivity` |
| **ProgressBar**| Visualizes current reading progress (animated). | `BookDetailsActivity` |

---

## 6. Technology & Event Handling

The application utilizes **Kotlin** for back-end logic, ensuring smooth interaction with the UI components.

### Event Listeners Used:
*   **`setOnClickListener`**: Used for all buttons to trigger navigation or action summaries.
*   **`setOnCheckedChangeListener`**: Used for the **CheckBox** (Favorites), **Switch** (Settings), and **RadioGroup** (Status) to handle state changes.
*   **`setOnRatingBarChangeListener`**: Updates a descriptive label in real-time as the user selects stars.
*   **`setOnItemSelectedListener`**: Used by the **Spinner** to respond when a new genre is selected from the dropdown.

---

## 7. Data Passing (Intent Extras)
The application maintains user context by passing data between Activities:
*   **`USER_NAME`**: Captured at login and displayed on the Home and Settings screens.
*   **`USER_USN`**: Passed throughout the app to personalize the experience.
*   **`BOOK_DATA`**: A Parcelable `Book` object passed from the Home/Genre screens to the `BookDetailsActivity`.

---

## 8. Experiment 5 Continuation
Experiment 5 notification functionality has been fully retained. Successful login triggers a **Login Notification**, and interacting with the "Add to Library" button triggers a **Reading Reminder Notification**.

---

## 9. User Flow
```mermaid
graph TD
    A[Login Screen] -->|Enter Name/USN| B[BookNest Home]
    B -->|Select Genre| C[Genre Details]
    C -->|Select Book| D[Book Details]
    B -->|Continue Reading| D
    D -->|Interact with Views| D
    D -->|Save Changes| E[Summary Toast]
    D -->|Read Online| F[Web Browser]
    D -->|Profile Icon| G[User Settings]
    G -->|Toggle Switches| G
    G -->|Logout| A
```

---

## 10. Project Structure
```text
BookNest/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/example/exp5notification/
│   │       │   ├── data/
│   │       │   │   ├── Book.kt (Parcelable Model)
│   │       │   │   └── BookRepository.kt (Dynamic Data)
│   │       │   └── ui/
│   │       │       ├── LoginActivity.kt
│   │       │       ├── MainActivity.kt
│   │       │       ├── HomeFragment.kt
│   │       │       ├── BookDetailsActivity.kt (Basic Views Focus)
│   │       │       ├── UserSettingsActivity.kt (Basic Views Focus)
│   │       │       ├── GenreDetailsActivity.kt
│   │       │       └── MagicBackgroundView.kt (Custom Animation)
│   │       ├── res/
│   │       │   ├── drawable/ (Gradients, Glass Styles, Vector Icons)
│   │       │   ├── layout/ (XML definitions for all screens)
│   │       │   └── values/ (Themes, Colors, Strings)
│   │       └── AndroidManifest.xml
│   └── build.gradle.kts
├── README.md
└── settings.gradle.kts
```

---
**Developed for MCA Android Development Lab**

---

## 11. Screenshots
Below are screenshots included in this repository demonstrating the use of basic Android Views in the BookNest application. Each screenshot highlights one or more views and includes a short description of what it demonstrates.

1. Login Screen - EditText & Button

![Login Screen](./Screenshot 2026-08-24 203553.png)

This screenshot shows the Login screen where users enter their Name and USN using EditText fields and submit via a Login Button. TextViews label each input field.

2. Home / Genre Selection - Spinner, TextView, ImageView

![Home / Genre Selection](./Screenshot 2026-08-24 203604.png)

This image demonstrates the Home/Genre selection view with a Spinner (dropdown) for genres, TextViews for headings, and ImageViews showing book thumbnails or category art.

3. Book Details - TextView, ImageView, RatingBar, ProgressBar

![Book Details](./Screenshot 2026-08-24 203622.png)

The Book Details screen uses TextViews for the title/author/description, an ImageView for the book cover, a RatingBar for user ratings, and a ProgressBar to visualize reading progress.

4. Interaction Controls - CheckBox, RadioButton, Switch

![Interaction Controls](./Screenshot 2026-08-24 203651.png)

This screenshot focuses on interactive controls in the Book Details screen: a CheckBox to "Add to Favorites", RadioButtons grouped for reading status (e.g., "Not Started", "Reading", "Finished"), and a Switch to toggle specific settings or reminders.

5. User Settings - Switches & TextViews

![User Settings](./ss.jpg)

The User Settings screen demonstrates multiple Switch controls (e.g., Notifications, Dark Mode) alongside descriptive TextViews.

6. Notes & Actions - EditText & Buttons

![Notes & Actions](./ss2.jpg)

Here the user can enter personal notes via an EditText and perform actions such as Save or Read using Buttons placed on the Book Details screen.

7. Spinner Interaction & Selection

![Spinner Interaction](./ss3.jpg)

This screenshot highlights Spinner interaction and the selected genre displayed via TextViews. The Spinner's selection listener updates UI elements based on the chosen genre.

8. RatingBar Feedback

![RatingBar Feedback](./ss4.jpg)

This view shows the RatingBar in use; selecting stars updates a descriptive label (e.g., "Very Good") via the `setOnRatingBarChangeListener`.

9. Reading Progress - ProgressBar Animation

![Reading Progress](./ss5.jpg)

A focused view of the ProgressBar demonstrating reading progress visualization; this can be updated programmatically to reflect pages or percentage completed.

10. Custom View / Decorative Background

![Custom View / Background](./ss6.jpg)

This screenshot highlights a custom animated background or decorative ImageView (referred to in the project as `MagicBackgroundView`) used to enhance visual appeal while standard Views provide the core functionality.

---

If you want any changes to these descriptions or specific captions for each image (for example, calling out exact layout XML files or line numbers), I can update the README accordingly.
