import { render, screen, fireEvent } from '@testing-library/react';
import { describe, it, expect, vi } from 'vitest';
import { TaskItem } from './TaskItem';
import type { Task } from '../types/task';

describe('TaskItem Component', () => {
  // 1. Arrange: Create mock data and functions
  const mockTask: Task = {
    id: 1,
    title: 'Test Task',
    description: 'This is a test description.',
    completed: false,
    createdAt: new Date().toISOString(),
  };

  const mockOnComplete = vi.fn(); // Creates a "spy" function

  it('should render the task title and description', () => {
    // 2. Act: Render the component with the mock data
    render(<TaskItem task={mockTask} onTaskComplete={mockOnComplete} />);

    // 3. Assert: Check if the content is on the screen
    expect(screen.getByText('Test Task')).toBeInTheDocument();
    expect(screen.getByText('This is a test description.')).toBeInTheDocument();
  });

  it('should call the onTaskComplete function when the Done button is clicked', () => {
    // 2. Act: Render the component and simulate a user click
    render(<TaskItem task={mockTask} onTaskComplete={mockOnComplete} />);
    
    const doneButton = screen.getByRole('button', { name: /done/i });
    fireEvent.click(doneButton);

    // 3. Assert: Check if our mock function was called
    expect(mockOnComplete).toHaveBeenCalledTimes(1);
    expect(mockOnComplete).toHaveBeenCalledWith(mockTask.id);
  });
});
