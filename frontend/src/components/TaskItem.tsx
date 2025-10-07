import type { Task } from '../types/task';

interface TaskItemProps {
  task: Task;
  onTaskComplete: (id: number) => void;
}

export function TaskItem({ task, onTaskComplete }: TaskItemProps) {
  return (
    <div className="bg-gray-100 rounded-lg p-4 flex justify-between items-center border border-gray-200">
      <div className="text-gray-800">
        <h3 className="font-bold text-lg">{task.title}</h3>
        <p className="text-gray-600 text-sm">{task.description}</p>
      </div>
      <button
        onClick={() => onTaskComplete(task.id)}
        className="bg-white hover:bg-gray-200 text-gray-700 font-semibold py-1 px-3 border border-gray-300 rounded-md transition-colors text-sm"
      >
        Done
      </button>
    </div>
  );
}