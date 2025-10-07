import { render, screen } from '@testing-library/react';
import { describe, it, expect, vi } from 'vitest';
import { TaskList } from './TaskList';
import type { Task } from '../types/task';

describe('TaskList Component', () => {
  const mockOnComplete = vi.fn();

  it('should render a list of tasks when tasks are provided', () => {
    // Arrange: Create a list of mock tasks
    const mockTasks: Task[] = [
      { id: 1, title: 'Task One', description: 'Desc 1', completed: false, createdAt: '' },
      { id: 2, title: 'Task Two', description: 'Desc 2', completed: false, createdAt: '' },
    ];
    
    // Act: Render the component
    render(<TaskList tasks={mockTasks} onTaskComplete={mockOnComplete} />);

    // Assert: Check that both task titles are visible
    expect(screen.getByText('Task One')).toBeInTheDocument();
    expect(screen.getByText('Task Two')).toBeInTheDocument();
  });

  it('should render an empty message when no tasks are provided', () => {
    // Arrange: Provide an empty array
    const mockTasks: Task[] = [];
    
    // Act: Render the component
    render(<TaskList tasks={mockTasks} onTaskComplete={mockOnComplete} />);
    
    // Assert: Check that the "No tasks" message is visible
    expect(screen.getByText('No tasks yet!')).toBeInTheDocument();
  });
});