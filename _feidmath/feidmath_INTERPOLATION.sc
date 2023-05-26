// NONE ---> INTERPOLATION_LINEAR
_INTERPOLATION_LINEAR = LAMBDA(x, known_ys, known_xs,
    LET(
        known_xs, TOCOL(known_xs),
        known_ys, TOCOL(known_ys),
        nrow, ROWS(known_ys),
        known_table, HSTACK(known_xs, known_ys),
        sorted_table, SORT(known_table, 1),
        sorted_xs, CHOOSECOLS(sorted_table, 1),
        sorted_ys, CHOOSECOLS(sorted_table, 2),
        nearest_x, IFS(
            x > MAX(sorted_xs),
            XMATCH(x, sorted_xs, -1),
            x < MIN(sorted_xs),
            XMATCH(x, sorted_xs, 1),
            TRUE,
            XMATCH(x, sorted_xs, -1)
        ),
        index_ys, IF(
            nearest_x < nrow,
            VSTACK(nearest_x, nearest_x + 1),
            VSTACK(nearest_x - 1, nearest_x)
        ),
        select_ys, CHOOSEROWS(sorted_ys, index_ys),
        select_xs, CHOOSEROWS(sorted_xs, index_ys),
        FORECAST.LINEAR(x, select_ys, select_xs)
    )
);

// _INTERPOLATION_LINEAR ---> INTERPOLATION_LINEAR_VECTOR
INTERPOLATION_LINEAR = LAMBDA(x_vector, known_y, known_x,
    LET(
        x_vector, TOCOL(x_vector),
        y_vector, BYROW(
            x_vector,
            LAMBDA(x, _INTERPOLATION_LINEAR(x, known_y, known_x))
        ),
        y_vector
    )
);
