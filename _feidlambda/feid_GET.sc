// NONE --> GET_INDEX_2D
GET_INDEX_2D = LAMBDA(lookup_value, array, [return_as_order],
    LET(
        return_as_order, IF(
            ISOMITTED(return_as_order),
            FALSE,
            return_as_order
        ),
        nrows, ROWS(array),
        ncols, COLUMNS(array),
        size, nrows * ncols,
        array_flatten, TOCOL(array, , TRUE),
        index_sequence, SEQUENCE(nrows, ncols, 1, 1),
        rows_sequence, MAKEARRAY(nrows, ncols, LAMBDA(x, y, x)),
        columns_sequence, MAKEARRAY(nrows, ncols, LAMBDA(x, y, y)),
        rows_flatten, TOCOL(rows_sequence, , TRUE),
        columns_flatten, TOCOL(columns_sequence, , TRUE),
        index_flatten, TOCOL(index_sequence, , TRUE),
        lookup_table, HSTACK(index_flatten, rows_flatten, columns_flatten),
        lookup_result, FILTER(lookup_table, array_flatten = lookup_value),
        IF(return_as_order, CHOOSECOLS(lookup_result, 1), lookup_result)
    )
);

// _RECURSIVE_LOOKUP --> _RECURSIVE_LOOKUP
_RECURSIVE_LOOKUP = LAMBDA(
    ntry,
    lookup_value,
    lookup_vector,
    return_array,
    [if_not_found],
    [match_mode],
    [search_mode],
    LET(
        lookup_value, TOCOL(lookup_value),
        LET(
            selected_value, VALUE(
                ARRAYTOTEXT(CHOOSEROWS(lookup_value, ntry))
            ),
            result, XLOOKUP(
                selected_value,
                lookup_vector,
                return_array,
                if_not_found,
                match_mode,
                search_mode
            ),
            IF(
                ntry = 1,
                result,
                VSTACK(
                    _RECURSIVE_LOOKUP(
                        ntry - 1,
                        lookup_value,
                        lookup_vector,
                        return_array,
                        if_not_found,
                        match_mode,
                        search_mode
                    ),
                    result
                )
            )
        )
    )
);

// GET_RECURSIVE_LOOKUP --> GET_XLOOKUP
GET_XLOOKUP = LAMBDA(
    lookup_value,
    lookup_vector,
    return_array,
    [if_not_found],
    [match_mode],
    [search_mode],
    LET(
        lookup_value, TOCOL(lookup_value),
        ntry, ROWS(lookup_value),
        _RECURSIVE_LOOKUP(
            ntry,
            lookup_value,
            lookup_vector,
            return_array,
            if_not_found,
            match_mode,
            search_mode
        )
    )
);
