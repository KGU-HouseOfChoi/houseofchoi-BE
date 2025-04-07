package com.noraknorak.sms.domain;

public interface AuthCodeManager {
    /**
     * 인증코드를 key값과 함께 redis에 저장합니다.
     * @param phoneNumber key 값
     * @param code value 값
     * @param ttl 초 단위 시간
     */
    void saveCode(String phoneNumber, String code, long ttl);

    /**
     * 지정한 전화번호에 저장된 인증 코드를 조회합니다.
     * @param phoneNumber 지정한 전화번호
     * @return 인증코드
     */
    String getCode(String phoneNumber);

    /**
     * 지정한 전화번호의 인증번호를 삭제합니다.
     * @param phoneNumber 지정한 전화번호
     */
    void deleteCode(String phoneNumber);
}
