package com.bibum_server.domain.exception;

public class ResuggestUnavailableException extends CustomException{

    public ResuggestUnavailableException() {
        super("400", "더이상 재추천할 수 없습니다");
    }
}
