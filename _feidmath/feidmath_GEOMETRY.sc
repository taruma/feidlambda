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
