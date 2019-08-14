package com.wootecobook.turkey.comment.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wootecobook.turkey.comment.service.CommentService;
import com.wootecobook.turkey.comment.service.dto.CommentResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CommentApiControllerTest {
    private static final String BASE_URI = "/api/posts/{postId}/comments";
    private static final Long POST_ID = 1L;

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CommentService commentService;

    @Test
    void 글목록_조회() throws IOException {
        // given
        final Pageable pageable = PageRequest.of(0, 20);
        final Page<CommentResponse> commentResponses = mock(Page.class);

        when(commentService.findAllByPostId(anyLong(), any()))
                .thenReturn(commentResponses);

        // when
        final EntityExchangeResult<byte[]> result = webTestClient.get().uri(BASE_URI + "?size={size}&page={page}", POST_ID, pageable.getPageSize(), pageable.getPageNumber())
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
                .expectBody().returnResult();

//        final String body = new String(result.getResponseBody());
//        final Page expected = new ObjectMapper().readValue(body, Page.class);

        // then
    }
}