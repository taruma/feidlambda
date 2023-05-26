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
