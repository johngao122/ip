# Quip User Guide

Quip is your friendly task management chatbot that helps you keep track of your todos, deadlines, and events with a dash of personality!


## Features

### 1. Managing Basic Tasks

#### Adding a Todo
Add a simple task without any date/time constraints.

Command: `todo TASK_DESCRIPTION`

Example: `todo buy groceries`

```
I've added this little nugget to your to-do list:
[T][ ] buy groceries
Now you have 1 tasks in the list.
```

#### Adding a Deadline
Add a task with a specific deadline.

Command: `deadline TASK_DESCRIPTION /by DATE_TIME`

Format: `YYYY-MM-DD HH:mm` (24-hour format)

Example: `deadline submit report /by 2024-02-15 23:59`

```
I've added this little nugget to your to-do list:
[D][ ] submit report (by: 2024-02-15 11:59 PM)
Now you have 2 tasks in the list.
```

#### Adding an Event
Add an event with start and end times.

Command: `event TASK_DESCRIPTION /from START_TIME /to END_TIME`

Format: `YYYY-MM-DD HH:mm` (24-hour format)

Example: `event team meeting /from 2024-02-16 14:00 /to 2024-02-16 15:00`

```
I've added this little nugget to your to-do list:
[E][ ] team meeting (from: 2024-02-16 2:00 PM to: 2024-02-16 3:00 PM)
Now you have 3 tasks in the list.
```

### 2. Viewing Tasks

#### List All Tasks
View all your tasks in chronological order.

Command: `list`

```
Here are the tasks in your list:
1. [T][ ] buy groceries
2. [D][ ] submit report (by: 2024-02-15 11:59 PM)
3. [E][ ] team meeting (from: 2024-02-16 2:00 PM to: 2024-02-16 3:00 PM)
```

#### View Tasks on a Specific Date
View all tasks scheduled for a particular date.

Command: `on DATE`

Format: `YYYY-MM-DD`

Example: `on 2024-02-16`

```
Here are the tasks for this date:
1. [E][ ] team meeting (from: 2024-02-16 2:00 PM to: 2024-02-16 3:00 PM)
```

#### Search Tasks
Find tasks containing specific keywords.

Command: `find KEYWORD`

Example: `find report`

```
Here are the matching tasks in your list:
1. [D][ ] submit report (by: 2024-02-15 11:59 PM)
```

### 3. Managing Task Status

#### Mark Task as Done
Mark a task as completed using its list number.

Command: `mark TASK_NUMBER`

Example: `mark 1`

```
Another one bites the dust: [T][X] buy groceries
```

#### Unmark Task
Mark a completed task as not done.

Command: `unmark TASK_NUMBER`

Example: `unmark 1`

```
Let's pretend that task wasn't done yet: [T][ ] buy groceries
```

### 4. Other Features

#### View Upcoming Tasks
See tasks due in the next 24 hours.

Command: `remind`

```
Here are your upcoming tasks:
  • Deadline approaching: submit report (in 2 hours 30 minutes)
```

#### Delete a Task
Remove a task from your list using its number.

Command: `delete TASK_NUMBER`

Example: `delete 1`

```
That task has vanished faster than your weekend plans. It's gone, mortal!
[T][ ] buy groceries
Now you have 2 tasks in the list.
```

#### Exit the Program
Close Quip when you're done.

Command: `bye`

```
Aww, you're leaving already? 😢 Bye for now!
```

## Automatic Features

### Task Reminders
Quip automatically checks for upcoming tasks every 15 minutes and will notify you about:
- Tasks due within the next 24 hours
- Events starting within the next 24 hours

## Error Handling

Quip will display helpful error messages when:
- Using invalid date/time formats (use: YYYY-MM-DD HH:mm)
- Referencing non-existent task numbers
- Adding duplicate tasks
- Using incorrect command formats

For the best experience, follow the command formats exactly as shown in the examples above.