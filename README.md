# SmartSpendMax

Group 21: Anjing Huang | Jiaxu Zhang | Meng Wang | Xiaoti Hu

### Project Overview

------



### Shared Doc

###### P1 Project Proposal 

https://docs.google.com/document/d/1tF81hIKujmxEJ9kSqhObG7jVYKOHkvLOFXTKGnMi9vQ/edit?usp=sharing

###### A6 At Your Service 

https://docs.google.com/document/d/1Z8i8EFDlK6MIIawu6CEVsSrGyE9FkDJ23iMix7_HD3M/edit?usp=sharing

------

### git tutorial & tips

#### Step 1: Ensure Your Working Branch is Up-to-Date

1. **Switch to the main branch**:

   ```sh
   git checkout main
   ```

2. **Pull the Latest Changes**:

   ```
   git pull origin main
   ```

#### Step 2: Create a New Branch for Your Work

```
git checkout -b feature_branch_name
```

#### Step 3: Work on Task and Commit

```
git add .
git commit -m "A descriptive commit message"
```

#### Step 4: Keep Branch Updated

o regularly update your branch with the latest changes from the main branch:

1. **Switch to the main branch and pull changes**:

   ```
   shCopy code
   git checkout main
   git pull origin main
   ```

2. **Switch back to your feature branch and merge or rebase**:

   For a merge:

   ```
   shCopy code
   git checkout feature_branch_name
   git merge main
   ```

   For a rebase:

   ```
   shCopy code
   git checkout feature_branch_name
   git rebase main
   ```

   Resolve any conflicts if they arise and complete the rebase or merge process.

#### Step 5: Rebase (Optional but Recommended for a Clean History)

If you prefer to rebase your feature branch onto the main branch before merging back to main:

1. Ensure you're on your feature branch and rebase onto main:

   ```
   shCopy code
   git rebase main
   ```

   Follow the prompts to resolve conflicts if necessary.

#### Step 6: Merge Your Changes

After your feature is complete, up-to-date, and approved via a pull request:

1. **Push your branch if you haven't yet**:

   ```
   shCopy code
   git push origin feature_branch_name
   ```

2. After merging your pull request through GitHub's interface, clean up your branches:

   Delete your remote feature branch(if it's not automatically done by GitHub):

   ```
   shCopy code
   git push origin --delete feature_branch_name
   ```

3. **Pull the latest main branch again**:

   ```
   shCopy code
   git checkout main
   git pull origin main
   ```

4. **Delete your local feature branch** (if desired):

   ```
   shCopy code
   git branch -d feature_branch_name
   ```

------

### References
