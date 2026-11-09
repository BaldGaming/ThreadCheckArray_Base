# Ilya Zeldner - Software Engineering Methods – Lab Exercise 2: Configuration Management & AI-Driven Refactoring

**Department of Software Engineering | Braude College of Engineering**  
**Course:** Software Engineering Methods (2026–2027)  
**Platform:** IntelliJ IDEA 2026.2 | BellSoft Liberica Full JDK 25 LTS | Git & GitHub | Continue (LM Studio)

---

## Executive Overview
In this lab exercise, students collaborate in pairs to modernize a multithreaded Java implementation of the classic **Subset Sum** decision problem. Working across isolated branches, students utilize a local, open-source AI coding assistant (**Continue** powered by **Qwen2.5-Coder**) to refactor legacy primitive arrays to modern generic collections (`ArrayList<Integer>`), generate strict JDK-compliant Javadoc documentation, intentionally introduce concurrent merge conflicts, and resolve them using IntelliJ IDEA's visual **3-Way Merge** tool.

### Core Learning Objectives
- **Modern Version Control:** Master real-world Git flows (initiating repositories, strict branch isolation, remote synchronizations, commit hygiene, and `.gitignore` governance).
- **AI-Assisted Modernization:** Leverage local LLMs for structured refactoring, code translation, and automated Javadoc authoring.
- **Conflict Engineering:** Synthesize divergent code paths and resolve line-level conflicts cleanly using 3-Way diff visualizers.
- **Quality Assurance & Traceability:** Execute verification smoke tests and evaluate individual code ownership through `git blame` (`Annotate with Git Blame`).

---

## Repository Directory Map
The repository is organized into three dedicated operational sections:

```text
braude-Engineering-Methods-Tutorial-2/
│
├── חומרי קריאה מקדימים והסברים/
│   ├── מדריך_מושגים_ועבודה_עם_Git_ו_GitHub_ב_IntelliJ.docx
│   │     └── Comprehensive Hebrew guide covering Git stages, branching, 
│   │         .gitignore, 3-Way Merge, and IntelliJ shortcuts.
│   └── מדריך_מלא_התקנה_תיקון_ועבודה_עם_Continue_ב_IntelliJ.docx
│         └── Complete setup guide for Continue, JetBrains Marketplace download,
│             IntelliJ 2026.2 JCEF compatibility patch, and LM Studio parameters.
│
├── קבצי עבודה לתרגול/
│   ├── תרגול 2.docx
│   │     └── The master laboratory instruction manual with step-by-step procedures.
│   ├── Quick_Checklist_Exercise_2.docx
│   │     └── An execution checklist for tracking task progression during the session.
│   ├── ThreadCheckArray_Base.zip
│   │     └── Starter Java project archive containing the base codebase and .gitignore.
│   └── README_TEMPLATE.md
│         └── Markdown template for student repository documentation.
│
└── הגשת המטלה/
    └── ex2_submission_template.docx
          └── Official laboratory report template with sections for team details,
              evidence screenshots, prompt logs, and engineering reflection.
```

---

## Required Toolchain & Setup
Before beginning the lab, ensure the following tools are properly configured:

| Tool | Version / Specification | Role in Lab |
| :--- | :--- | :--- |
| **IntelliJ IDEA** | 2026.2 or newer | Core Integrated Development Environment |
| **Java SDK** | BellSoft Liberica Full JDK 25 LTS (with JavaFX) | Compilation & runtime platform |
| **Git Engine** | Git for Windows 2.40+ | Local version control engine |
| **GitHub Account** | Active student account | Remote team collaboration host |
| **LM Studio** | Local Server on `http://127.0.0.1:1234/v1` | Hosts local `Qwen2.5-Coder-7B-Instruct` |
| **Continue** | IntelliJ Plugin (v1.0.67 + JCEF patch) | AI code assistant & inline refactoring interface |

---

## Student Pair Programming Workflow Summary

```text
Student A (main branch)                    Student B (feature branch)
       │                                              │
       ├─► Init Repo & Initial Commit                 │
       ├─► Push to GitHub & Invite Partner ───────────┼─► Accept Invite & Clone Repo
       │                                              ├─► Create branch: feature/arraylist-refactor-b
       ├─► Refactor SharedData.java                   ├─► Write Custom Prompt Challenge
       ├─► Refactor ThreadCheckArray.java             ├─► Refactor TestThreadCheckArray.java
       ├─► Commit & Push to main                      ├─► Refactor ThreadCheckArray.java
       ├─► Generate & Push README.md                  ├─► Commit & Push feature branch
       │                                              │
       │                                              ├─► Switch to main & Pull partner commits
       │                                              ├─► Merge feature branch into main
       │                                              ├─► 💥 MERGE CONFLICT DETECTED 💥
       │                                              ├─► Resolve via visual 3-Way Merge
       │                                              ├─► Execute Smoke Test Verification
       │                                              └─► Push resolved main to GitHub
       ▼                                              ▼
Student A pulls resolved main ◄───────────────────────┘
Both verify via HTML Javadoc, Git Graph, and Annotate with Git Blame.
```

---

## Verification & Smoke Test Protocol
Following conflict resolution and merge completion, execute `TestThreadCheckArray.java` using the standard verification input vector:

### Test Input:
```text
Enter array size:
4
Enter numbers for array:
1 2 3 7
Enter number:
5
```

### Expected Output:
```text
Solution for b : 5,n = 4
I:    0    1    2    3    
A:    1    2    3    7    
C:    0    1    1    0    
```
* **Explanation:** The binary solution vector row `C: 0 1 1 0` indicates that index `1` (value `2`) and index `2` (value `3`) form the subset sum equal to the target value `5` ($2 + 3 = 5$).

---

## Essential Troubleshooting Tips

- **Built-in Terminal Profile Conflict:** If using Windows PowerShell with Anaconda/Conda, append `-NoProfile` in `Settings -> Tools -> Terminal -> Shell path`.
- **First-Time GitHub Authentication:** When pushing or cloning for the first time, click **Log In via GitHub...**, approve the browser authorization prompt, and return to IntelliJ.
- **Git Identity Prompt:** If IntelliJ displays "User Name and Email not configured", enter your full English name and institutional email (`username@e.braude.ac.il`).
- **Unrelated Histories Error:** If partner repositories were created independently rather than cloned, resolve the history divergence in the terminal using:
  ```bash
  git merge feature/arraylist-refactor-b --allow-unrelated-histories
  ```
- **Javadoc Malformed HTML Warning:** Generic angle brackets (`<Integer>`) will fail Javadoc compilation. Ensure all references inside Javadoc comments are enclosed with `{@code <Integer>}`.

---

## Academic Integrity & Licensing
This laboratory package is developed for instructional use in the Department of Software Engineering at Braude College of Engineering. Distribution, publishing, or sharing outside the official course boundary is strictly prohibited under the **Braude College Academic License**.
