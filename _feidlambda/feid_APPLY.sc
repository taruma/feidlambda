// NONE --> APPLY_COLUMN
APPLY_COLUMN = LAMBDA(array, index_vector, LAMBDA_FUNCTION,
    LET(
        index_vector, SORT(index_vector),
        selected_array, CHOOSECOLS(array, index_vector),
        applied_array, LAMBDA_FUNCTION(selected_array),
        sequence_vector, SEQUENCE(COLUMNS(array)),
        logical_vector, BYROW(
            sequence_vector,
            LAMBDA(row, OR(row = index_vector))
        ),
        scan_vector, SCAN(
            0,
            logical_vector,
            LAMBDA(acc, curr, IF(curr, acc + 1, acc))
        ),
        position_vector, scan_vector + COLUMNS(array),
        all_array, HSTACK(array, applied_array),
        selected_vector, MAP(
            logical_vector,
            sequence_vector,
            position_vector,
            LAMBDA(logical_el, seq_el, pos_el,
                IF(logical_el, pos_el, seq_el)
            )
        ),
        CHOOSECOLS(all_array, selected_vector)
    )
);
