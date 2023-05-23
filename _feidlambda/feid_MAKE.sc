// _RECURSIVE_MAKE_SEQUENCE --> _RECURSIVE_MAKE_SEQUENCE
_RECURSIVE_MAKE_SEQUENCE = LAMBDA(
    start_vector,
    end_vector,
    ntry,
    [stack_horizontally],
    LET(
        seq_start, INDEX(start_vector, ntry),
        seq_end, INDEX(end_vector, ntry),
        stack_horizontally, IF(
            ISOMITTED(stack_horizontally),
            FALSE,
            stack_horizontally
        ),
        IF(
            ntry = 1,
            SEQUENCE(seq_end - seq_start + 1, , seq_start),
            LET(
                next_try, ntry - 1,
                results, SEQUENCE(seq_end - seq_start + 1, , seq_start),
                IF(
                    stack_horizontally,
                    HSTACK(
                        _RECURSIVE_MAKE_SEQUENCE(
                            start_vector,
                            end_vector,
                            next_try,
                            stack_horizontally
                        ),
                        results
                    ),
                    VSTACK(
                        _RECURSIVE_MAKE_SEQUENCE(
                            start_vector,
                            end_vector,
                            next_try,
                            stack_horizontally
                        ),
                        results
                    )
                )
            )
        )
    )
);

// _RECURSIVE_MAKE_SEQUENCE --> MAKE_SEQUENCE_FROM_VECTOR
MAKE_SEQUENCE_FROM_VECTOR = LAMBDA(
    start_vector,
    end_vector,
    [stack_horizontally],
    _RECURSIVE_MAKE_SEQUENCE(
        start_vector,
        end_vector,
        ROWS(start_vector),
        stack_horizontally
    )
);
