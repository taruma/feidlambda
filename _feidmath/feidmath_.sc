/*
feidmath v0.1.0 - MATH FUNCTIONS BY FIAKO ENGINEERING
OFFICIAL GIST (feidmath v0.1.x): 
    https://gist.github.com/taruma/8b0978227dffbee50c3a9d56e31d34f3
REPOSITORY: 
    https://github.com/fiakoenjiniring/feidlambda
CONTRIBUTOR: @taruma, @iingLK
TESTED: Microsoft Excel 365 v2304
*/

// BATAS MAKSMIMUM LAYAR EDITOR -------------------------------------------#

/*
---- INTERPOLATION ----
*/

// NONE ---> _INTERPOLATION_LINEAR
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

// _INTERPOLATION_LINEAR ---> INTERPOLATION_LINEAR
INTERPOLATION_LINEAR = LAMBDA(x_vector, known_ys, known_xs,
    LET(
        x_vector, TOCOL(x_vector),
        y_vector, BYROW(
            x_vector,
            LAMBDA(x, _INTERPOLATION_LINEAR(x, known_ys, known_xs))
        ),
        y_vector
    )
);

/*
---- LINEAR ALGEBRA (LINALG) ----
*/

// NONE ---> LINALG_ROTATION_MATRIX
LINALG_ROTATION_MATRIX = LAMBDA(theta_x, theta_y, theta_z, [num_digits],
    LET(
        round_number, IF(ISOMITTED(num_digits), 0, num_digits),
        angle_x, RADIANS(theta_x),
        angle_y, RADIANS(theta_y),
        angle_z, RADIANS(theta_z),
        cos_x, COS(angle_x),
        sin_x, SIN(angle_x),
        rotation_x, VSTACK(
            HSTACK(1, 0, 0),
            HSTACK(0, cos_x, -sin_x),
            HSTACK(0, sin_x, cos_x)
        ),
        cos_y, COS(angle_y),
        sin_y, SIN(angle_y),
        rotation_y, VSTACK(
            HSTACK(cos_y, 0, sin_y),
            HSTACK(0, 1, 0),
            HSTACK(-sin_y, 0, cos_y)
        ),
        cos_z, COS(angle_z),
        sin_z, SIN(angle_z),
        rotation_z, VSTACK(
            HSTACK(cos_z, -sin_z, 0),
            HSTACK(sin_z, cos_z, 0),
            HSTACK(0, 0, 1)
        ),
        rotation_matrix, MMULT(rotation_z, MMULT(rotation_y, rotation_x)),
        IF(
            round_number,
            ROUND(rotation_matrix, round_number),
            rotation_matrix
        )
    )
);

// LINALG_ROTATION_MATRIX ---> LINALG_ROTATE_POINT
LINALG_ROTATE_POINT = LAMBDA(
    point_vector,
    theta_x,
    theta_y,
    theta_z,
    [active_rotation],
    [num_digits],
    LET(
        active_rotation, IF(
            ISOMITTED(active_rotation),
            TRUE,
            active_rotation
        ),
        rotation_matrix, LINALG_ROTATION_MATRIX(
            theta_x,
            theta_y,
            theta_z,
            num_digits
        ),
        point_vector, TOCOL(point_vector),
        final_rotation, IF(
            active_rotation,
            rotation_matrix,
            TRANSPOSE(rotation_matrix)
        ),
        point_rotation, MMULT(final_rotation, point_vector),
        TOROW(point_rotation)
    )
);

// LINALG_ROTATE_POINT ---> _RECURSIVE_ROTATE_POINTS
// _RECURSIVE_ROTATE_POINTS ---> _RECURSIVE_ROTATE_POINTS
_RECURSIVE_ROTATE_POINTS = LAMBDA(
    ntry,
    data_points,
    theta_x,
    theta_y,
    theta_z,
    [active_rotation],
    [num_digits],
    LET(
        selected_row, CHOOSEROWS(data_points, ntry),
        IF(
            ntry = 1,
            LINALG_ROTATE_POINT(
                selected_row,
                theta_x,
                theta_y,
                theta_z,
                active_rotation,
                num_digits
            ),
            LET(
                next_try, ntry - 1,
                result, LINALG_ROTATE_POINT(
                    selected_row,
                    theta_x,
                    theta_y,
                    theta_z,
                    active_rotation,
                    num_digits
                ),
                VSTACK(
                    _RECURSIVE_ROTATE_POINTS(
                        next_try,
                        data_points,
                        theta_x,
                        theta_y,
                        theta_z,
                        active_rotation,
                        num_digits
                    ),
                    result
                )
            )
        )
    )
);

// _RECURSIVE_ROTATE_POINTS ---> LINALG_ROTATE_POINT_ARRAY
LINALG_ROTATE_POINT_ARRAY = LAMBDA(
    data_points,
    theta_x,
    theta_y,
    theta_z,
    [active_rotation],
    [num_digits],
    _RECURSIVE_ROTATE_POINTS(
        ROWS(data_points),
        data_points,
        theta_x,
        theta_y,
        theta_z,
        active_rotation,
        num_digits
    )
);

/*
---- GEOMETRY ----
*/

// NONE ---> GEOMETRY_POINT_IN_POLYGON
GEOMETRY_IS_POINT_IN_POLYGON = LAMBDA(point_vector, data_polygon,
    LET(
        point_vector, TOCOL(point_vector),
        xp, INDEX(point_vector, 1),
        yp, INDEX(point_vector, 2),
        data_1, DROP(data_polygon, -1),
        data_2, DROP(data_polygon, 1),
        data_joined, HSTACK(data_1, data_2),
        _x1, CHOOSECOLS(data_joined, 1),
        _y1, CHOOSECOLS(data_joined, 2),
        _x2, CHOOSECOLS(data_joined, 3),
        _y2, CHOOSECOLS(data_joined, 4),
        first_condition, (yp < _y1) <> (yp < _y2),
        second_condition, xp <
            (_x1 + (((yp - _y1) / (_y2 - _y1)) * (_x2 - _x1))),
        final_condition, IFERROR(
            (first_condition * second_condition) = 1,
            FALSE
        ),
        is_inside, MOD(SUM(INT(final_condition)), 2) = 1,
        is_inside
    )
);

// GEOMETRY_IS_POINT_IN_POLYGON ---> GEOMETRY_ARE_POINTS_IN_POLYGON
GEOMETRY_ARE_POINTS_IN_POLYGON = LAMBDA(data_points, polygon_points,
    BYROW(
        data_points,
        LAMBDA(row, GEOMETRY_IS_POINT_IN_POLYGON(row, polygon_points))
    )
);