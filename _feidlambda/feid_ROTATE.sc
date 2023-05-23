// NONE --> ROTATE_VECTOR
ROTATE_VECTOR = LAMBDA(vector, num_rotation, [as_column_vector],
    LET(
        vector, TOCOL(vector),
        rotated_array, IFS(
            OR(
                num_rotation = 0,
                num_rotation >= ROWS(vector),
                num_rotation <= -ROWS(vector)
            ),
            vector,
            num_rotation > 0,
            VSTACK(DROP(vector, num_rotation), TAKE(vector, num_rotation)),
            num_rotation < 0,
            VSTACK(TAKE(vector, num_rotation), DROP(vector, num_rotation))
        ),
        as_column_vector, IF(ISOMITTED(as_column_vector), FALSE, TRUE),
        IF(as_column_vector, TOROW(rotated_array), TOCOL(rotated_array))
    )
);

// ROTATE_VECTOR --> ROTATE_ARRAY
ROTATE_ARRAY = LAMBDA(array, num_rotation, [rotate_columns],
    LET(
        rotate_columns, IF(ISOMITTED(rotate_columns), TRUE, FALSE),
        nrows, ROWS(array),
        ncols, COLUMNS(array),
        seqrows, SEQUENCE(nrows),
        seqcols, SEQUENCE(1, ncols),
        results, IF(
            rotate_columns,
            CHOOSECOLS(array, ROTATE_VECTOR(seqcols, num_rotation, TRUE)),
            CHOOSEROWS(array, ROTATE_VECTOR(seqrows, num_rotation, FALSE))
        ),
        results
    )
);
