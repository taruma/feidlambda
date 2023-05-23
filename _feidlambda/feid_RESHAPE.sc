// NONE --> RESHAPE_BY_COLUMNS
RESHAPE_BY_COLUMNS = LAMBDA(array, [num_split],
    LET(
        num_split, IF(ISOMITTED(num_split), 2, num_split),
        ncols, COLUMNS(array),
        nrows, ROWS(array),
        IF(
            MOD(ncols, num_split) = 0,
            LET(
                divider, ncols / num_split,
                divider_sequence, CHOOSEROWS(
                    SEQUENCE(1, divider),
                    SEQUENCE(num_split, , 1, 0)
                ),
                divider_flatten, TOCOL(divider_sequence, , TRUE),
                divider_repeat, CHOOSEROWS(
                    TOROW(divider_flatten),
                    SEQUENCE(nrows, , 1, 0)
                ),
                divider_repeat_col, TOCOL(divider_repeat),
                array_flatten, TOCOL(array),
                array_sorted, SORTBY(array_flatten, divider_repeat_col),
                WRAPROWS(array_sorted, num_split)
            ),
            NA()
        )
    )
);
