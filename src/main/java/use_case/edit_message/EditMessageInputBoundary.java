package use_case.edit_message;

/**
 * Input Boundary for actions which are related to editing messages.
 */
public interface EditMessageInputBoundary {
    /**
     * Executes the edit message use case.
     * @param editMessageInputData the input data
     */
    void execute(EditMessageInputData editMessageInputData);
}
