# Novella

A small in-development application that uses kotlin basics to assist in tracking storylines and characters within a story. 

---

## Screens 
Home: Where the magic happens, add names, add themes, count up points, and keep track of progress.
List: Searchable screen with all stored names.
About: Informative screen regarding the developer and roadmap.

## Input Rules
**Character Names:** 
  - Only letters, hyphens, or apostrophes 
  - Must be between 3 and 40 letters
  - Must follow correct casing, e.g. Camel Case.
**Themes:**
  - Scrollable, no newlines: single line only.

  ### Character Example Texts

|     | I typed         | What the app did    | 
| --- | --------------- | ------------------- | 
| 1   |                 | Denied              |
| 2   | "  "            | Denied              |
| 3   | Abaracadabara   | Allowed             |
| 4   | Abaracada56bara | Denied              |
| 5   | Abacacdabara's  | Allowed             |
| 6   | Abac-acadabara' | Alowed              |
| 7   | A$ba(acadabara  | Denied              |
| 8   | bracadabra      | Denied              |
| 9   | Brac Dabra      | Allowed             |

## How to run
1. Clone this repo
2. Open the cloned folder in Android Studio
3. Wait for Gradle sync, then Run

## Known Limitations: 
This app is not capable of multiple sessons, so closing all tabs removes all work done within.
