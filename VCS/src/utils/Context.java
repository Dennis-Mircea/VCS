/*
 * Copyright 2018 <Ciupitu Dennis-Mircea 323CA>
 */
package utils;

import filesystem.File;
import filesystem.Directory;
import vcs.Vcs;

public final class Context {
    private Vcs vcs;
    private static Context instance = null;
    private InputReader inputReader;
    private OutputWriter outputWriter;

    /**
     * Context constructor.
     */
    private Context() {
    }

    /**
     * Gets the instance.
     *
     * @return the instance
     */
    public static Context getInstance() {
        if (instance == null) {
            instance = new Context();
        }

        return instance;
    }

    /**
     * Initialise the vcs.
     *
     * @param input  the input file
     * @param output the output file
     */
    public void init(String input, String output) {
        inputReader = new InputReader(input);
        outputWriter = new OutputWriter(output);
        vcs = new Vcs(outputWriter);
    }

    /**
     * Runs the context.
     */
    public void run() {
        String operationString = "";
        AbstractOperation operation;
        OperationParser parser = new OperationParser();
        int exitCode;
        boolean wasError;

        this.vcs.init();

        while (true) {
            operationString = this.inputReader.readLine();
            if (operationString == null || operationString.isEmpty()) {
                continue;
            }
            if (operationString.equals("exit")) {
                return;
            }

            operation = parser.parseOperation(operationString);
            exitCode = operation.accept(vcs);
            wasError = ErrorCodeManager.getInstance().checkExitCode(outputWriter, exitCode);

            if (!wasError && this.isTrackable(operation)) {
                // TODO If the operation is trackable, vcs should track it
                // daca s-a creat fisier adauga continut stage-ului si
                // salveaza fisierul in stage
                if (operation.getType().equals(OperationType.TOUCH)) {
                    vcs.addContentStage("\tCreated file "
                            + operation.getOperationArgs().get(1) + "\n");
                    File file = (File) vcs.getActiveSnapshot().
                            getEntity(operation.getOperationArgs().get(1));
                    vcs.getStage().addFile(file);
                }
                // actualizeaza stage si pentru un director creat
                if (operation.getType().equals(OperationType.MAKEDIR)) {
                    vcs.addContentStage("\tCreated directory "
                            + operation.getOperationArgs().get(1) + "\n");
                    Directory dir = (Directory) vcs.getActiveSnapshot().
                            getEntity(operation.getOperationArgs().get(1));
                    vcs.getStage().addDirectory(dir);
                }
                // actualizeaza stage si pentru scrierea in fisier
                if (operation.getType().equals(OperationType.WRITETOFILE)) {
                    vcs.addContentStage("\tAdded \""
                            + operation.getOperationArgs().get(1)
                            + "\" to file "
                            + operation.getOperationArgs().get(0) + "\n");
                }
            }
        }
    }

    /**
     * Specifies if an operation is vcs trackable or not.
     * You can use it when you implement rollback/checkout -c functionalities.
     * @param abstractOperation the operation
     * @return whether it's trackable or not
     */
    private boolean isTrackable(AbstractOperation abstractOperation) {
        boolean result;

        switch (abstractOperation.type) {
            case CHANGEDIR:
            case MAKEDIR:
            case REMOVE:
            case TOUCH:
            case WRITETOFILE:
                result = true;
                break;
            default:
                result = false;
        }

        return result;
    }
}
