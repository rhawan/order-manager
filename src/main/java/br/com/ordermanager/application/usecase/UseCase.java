package br.com.ordermanager.application.usecase;

public interface UseCase<INPUT, OUTPUT> {
    OUTPUT execute(INPUT input);
}
