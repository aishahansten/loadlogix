package org.ssafy.load.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ssafy.load.common.dto.ErrorCode;
import org.ssafy.load.common.exception.CommonException;
import org.ssafy.load.dao.WorkerRepository;
import org.ssafy.load.domain.WorkerEntity;
import org.ssafy.load.dto.request.LoginRequest;
import org.ssafy.load.dto.request.SignUpRequest;
import org.ssafy.load.dto.response.LoginResponse;
import org.ssafy.load.dto.response.SignUpResponse;
import org.ssafy.load.dto.response.WorkerInfoResponse;
import org.ssafy.load.dto.response.WorkerResponse;
import org.ssafy.load.security.JwtTokenProvider;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkerService {

    private final JwtTokenProvider jwtTokenProvider;
    private final WorkerRepository workerRepository;

    public SignUpResponse signup(SignUpRequest signUpRequest) {
        Optional<WorkerEntity> worker = workerRepository.findByLoginId(signUpRequest.id());
        if (worker.isPresent()) {
            throw new CommonException(ErrorCode.USER_ALREADY_EXISTS);
        }
        return SignUpResponse.from(workerRepository.save(WorkerEntity.createNewWorker(
                signUpRequest.id(), signUpRequest.password(), signUpRequest.name(), null)
        ));
    }

    public LoginResponse login(LoginRequest loginRequest) {
        Optional<WorkerEntity> workerOptional = workerRepository.findByLoginIdAndPassword(
                loginRequest.id(),
                loginRequest.password());

        WorkerEntity worker = workerOptional.orElseThrow(() -> new CommonException(ErrorCode.USER_NOT_FOUND));
        if(worker.getArea() == null) throw new CommonException(ErrorCode.AREA_NOT_FOUND);

        return LoginResponse.of(jwtTokenProvider.generateToken(worker.getId(), worker.getName(),"worker"));
    }

    @Transactional(readOnly = true)
    public WorkerInfoResponse getWorkerInfo(Long workerId){
        Optional<WorkerEntity> workerOptional = workerRepository.findById(workerId);
        WorkerEntity worker = workerOptional.orElseThrow(() -> new CommonException(ErrorCode.USER_NOT_FOUND));
        return WorkerInfoResponse.from(worker);
    }

    @Transactional(readOnly = true)
    public List<WorkerResponse> getWorkerList() {
        List<WorkerEntity> workers = workerRepository.findAll();
        return workers.stream()
                .map(worker -> new WorkerResponse(worker.getName(), worker.getArea().getAreaName()))
                .toList();
    }
}
