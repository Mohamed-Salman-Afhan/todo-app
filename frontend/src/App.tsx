import { useState, useEffect } from 'react';
import type { Task } from './types/task';
import axios from 'axios';
import { TaskList } from './components/TaskList';
import { AddTaskForm } from './components/AddTaskForm';

const API_URL = import.meta.env.VITE_API_URL;

function App() {
  const [tasks, setTasks] = useState<Task[]>([]);

  useEffect(() => {
    fetchTasks();
  }, []);

  const fetchTasks = async () => {
    try {
      const response = await axios.get(API_URL);
      setTasks(response.data);
    } catch (error) {
      console.error("Error fetching tasks:", error);
    }
  };

  const handleAddTask = async (title: string, description: string) => {
    try {
      await axios.post(API_URL, { title, description });
      fetchTasks();
    } catch (error) {
      console.error("Error adding task:", error);
    }
  };

  const handleTaskComplete = async (id: number) => {
    try {
      await axios.patch(`${API_URL}/${id}`);
      fetchTasks();
    } catch (error) {
      console.error("Error completing task:", error);
    }
  };

  return (
  <div className="bg-gray-50 min-h-screen font-sans p-8">
    <div className="w-full max-w-4xl mx-auto">
        <div className="grid grid-cols-1 md:grid-cols-2 border border-gray-200 rounded-lg shadow-sm bg-white">
            <div className="p-8 border-r border-gray-200">
                <h1 className="text-2xl font-semibold mb-6 text-gray-800">Add a Task</h1>
                <AddTaskForm onAddTask={handleAddTask} />
            </div>
            <div className="p-8">
                <TaskList tasks={tasks} onTaskComplete={handleTaskComplete} />
            </div>
        </div>
    </div>
</div>
  );
}

export default App;