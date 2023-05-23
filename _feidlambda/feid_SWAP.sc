// NONE --> SWAP_COLUMNS
SWAP_COLUMNS = LAMBDA(array, [from_index], [to_index],
    LET(
        ncols, COLUMNS(array),
        from_index, IF(ISOMITTED(from_index), 1, from_index),
        to_index, IF(ISOMITTED(to_index), -1, to_index),
        from_value, IF(from_index < 0, from_index + ncols + 1, from_index),
        to_value, IF(to_index < 0, to_index + ncols + 1, to_index),
        column_sequence, SEQUENCE(1, COLUMNS(array)),
        from_logical, column_sequence = from_value,
        to_logical, column_sequence = to_value,
        replace_from, IF(from_logical, to_value, column_sequence),
        replace_to, IF(to_logical, from_value, replace_from),
        CHOOSECOLS(array, replace_to)
    )
);

// NONE --> SWAP_ROWS
SWAP_ROWS = LAMBDA(array, [from_index], [to_index],
    LET(
        nrows, ROWS(array),
        from_index, IF(ISOMITTED(from_index), 1, from_index),
        to_index, IF(ISOMITTED(to_index), -1, to_index),
        from_value, IF(from_index < 0, from_index + nrows + 1, from_index),
        to_value, IF(to_index < 0, to_index + nrows + 1, to_index),
        row_sequence, SEQUENCE(ROWS(array)),
        from_logical, row_sequence = from_value,
        to_logical, row_sequence = to_value,
        replace_from, IF(from_logical, to_value, row_sequence),
        replace_to, IF(to_logical, from_value, replace_from),
        CHOOSEROWS(array, replace_to)
    )
);
