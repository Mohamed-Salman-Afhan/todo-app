import type { Task } from '../types/task';
import { TaskItem } from './TaskItem';

interface TaskListProps {
  tasks: Task[];
  onTaskComplete: (id: number) => void;
}

export function TaskList({ tasks, onTaskComplete }: TaskListProps) {
  return (
    <div className="space-y-3">
      {tasks.length > 0 ? (
        tasks.map(task => (
          <TaskItem key={task.id} task={task} onTaskComplete={onTaskComplete} />
        ))
      ) : (
        <p className="text-slate-500">No tasks yet!</p>
      )}
    </div>
  );
}