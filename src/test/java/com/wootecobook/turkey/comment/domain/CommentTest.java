package com.wootecobook.turkey.comment.domain;

import com.wootecobook.turkey.post.domain.Post;
import com.wootecobook.turkey.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentTest {

    private Comment comment;

    @Mock
    private Post post;

    @Mock
    private User user;

    @BeforeEach
    void setUp() {
        comment = Comment.builder()
                .contents("contents")
                .parent(null)
                .post(post)
                .user(user)
                .build();
    }

    @Test
    void 작성자_맞는_경우() {
        // given
        final long userId = 1L;
        when(user.getId()).thenReturn(userId);

        // when & then
        assertThat(comment.isWrittenBy(userId)).isTrue();
    }

    @Test
    void 작성자_아니면_예외처리() {
        // given
        final long userId = 1L;
        final long otherUserId = 2L;

        when(user.getId()).thenReturn(userId);

        // when & then
        assertThrows(CommentAuthException.class, () -> comment.isWrittenBy(otherUserId));
    }

    @Test
    void 수정_확인() {
        // given
        Comment other = Comment.builder()
                .contents("수정_확인")
                .build();
        // when
        comment.update(other);

        // then
        assertThat(comment.getContents()).isEqualTo(other.getContents());
    }
}