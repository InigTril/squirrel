package org.squirrelframework.foundation.fsm;

/**
 * Defines the history behavior of a state (on re-entrance of a super state).
 * 
 * @author Henry.He
 *
 */
public enum HistoryType {
    /**
     * The state enters into its initial sub-state. The sub-state itself enters its initial sub-state and so on until the innermost nested
     * state is reached. This is the default.
     */
    NONE,

    /**
     * The state enters into its last active sub-state. The sub-state itself enters its initial sub-state and so on until the innermost
     * nested state is reached.
     */
    SHALLOW,

    /**
     * The state enters into its last active sub-state. The sub-state itself enters its last active sub-state if it has
     * recursive history too and so on until the sub-state has no recursive history or the innermost nested state is reached.
     */
    RECURSIVE,

    /**
     * The state enters into its last active sub-state. The sub-state itself enters into-its last active state and so on until the innermost
     * nested state is reached.
     */
    DEEP
}
