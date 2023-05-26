// _RECURSIVE_TEXT_SPLIT --> _RECURSIVE_TEXT_SPLIT
_RECURSIVE_TEXT_SPLIT = LAMBDA(
    text_vector,
    ntry,
    col_delimiter,
    [row_delimiter],
    [ignore_empty],
    [match_mode],
    [pad_with],
    LET(
        text_vector, TOCOL(text_vector),
        selected_row, ARRAYTOTEXT(INDEX(text_vector, ntry)),
        IF(
            ntry = 1,
            TEXTSPLIT(
                selected_row,
                col_delimiter,
                row_delimiter,
                ignore_empty,
                match_mode,
                pad_with
            ),
            LET(
                next_try, ntry - 1,
                results, TEXTSPLIT(
                    selected_row,
                    col_delimiter,
                    row_delimiter,
                    ignore_empty,
                    match_mode,
                    pad_with
                ),
                VSTACK(
                    _RECURSIVE_TEXT_SPLIT(
                        text_vector,
                        next_try,
                        col_delimiter,
                        row_delimiter,
                        ignore_empty,
                        match_mode,
                        pad_with
                    ),
                    results
                )
            )
        )
    )
);

// _RECURSIVE_TEXT_SPLIT --> TEXT_SPLIT_VECTOR
TEXT_SPLIT_VECTOR = LAMBDA(
    text_vector,
    [col_delimiter],
    [row_delimiter],
    [ignore_empty],
    [match_mode],
    [pad_with],
    [replace_na],
    LET(
        nrows, ROWS(text_vector),
        col_delimiter, IF(ISOMITTED(col_delimiter), " ", col_delimiter),
        replace_na, IF(ISOMITTED(replace_na), NA(), replace_na),
        pad_with, IF(ISOMITTED(pad_with), "", pad_with),
        result, _RECURSIVE_TEXT_SPLIT(
            text_vector,
            nrows,
            col_delimiter,
            row_delimiter,
            ignore_empty,
            match_mode,
            pad_with
        ),
        IFERROR(result, replace_na)
    )
);
