import { render, screen, fireEvent } from '@testing-library/react';
import { describe, it, expect, vi } from 'vitest';
import { AddTaskForm } from './AddTaskForm';

describe('AddTaskForm Component', () => {
  it('should call onAddTask with the correct data when the form is submitted', () => {
    // 1. Arrange
    const mockOnAddTask = vi.fn();
    render(<AddTaskForm onAddTask={mockOnAddTask} />);
    
    // Find form elements
    const titleInput = screen.getByLabelText(/title/i) as HTMLInputElement;
    const descriptionInput = screen.getByLabelText(/description/i) as HTMLInputElement;
    const addButton = screen.getByRole('button', { name: /add/i });

    // 2. Act
    // Simulate user typing
    fireEvent.change(titleInput, { target: { value: 'New Test Task' } });
    fireEvent.change(descriptionInput, { target: { value: 'A description' } });
    
    // Simulate user clicking the add button
    fireEvent.click(addButton);

    // 3. Assert
    // Check if our mock function was called once
    expect(mockOnAddTask).toHaveBeenCalledTimes(1);

    // Check if it was called with the data we typed
    expect(mockOnAddTask).toHaveBeenCalledWith('New Test Task', 'A description');
    
    // Check if the form cleared itself after submission
    expect(titleInput.value).toBe('');
    expect(descriptionInput.value).toBe('');
  });
});