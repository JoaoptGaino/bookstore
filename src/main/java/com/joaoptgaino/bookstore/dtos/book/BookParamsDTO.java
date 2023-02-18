package com.joaoptgaino.bookstore.dtos.book;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class BookParamsDTO {
    private String title;
    private String summary;
}
