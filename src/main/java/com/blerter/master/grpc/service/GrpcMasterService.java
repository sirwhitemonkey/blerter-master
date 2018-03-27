package com.blerter.master.grpc.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

import com.blerter.constant.Status;
import com.blerter.factory.UtilsFactory;
import com.blerter.grpc.service.MasterServiceGrpc;
import com.blerter.model.Id;
import com.blerter.model.InternalRequest;
import com.blerter.model.Response;
import com.blerter.persistence.Health;
import com.blerter.service.MasterService;

/**
 * Grpc master service
 *
 */
@GRpcService
public class GrpcMasterService extends MasterServiceGrpc.MasterServiceImplBase {

	private static Logger logger = LogManager.getLogger(GrpcMasterService.class);
	private transient UtilsFactory utilsFactory = UtilsFactory.factory();

	@Autowired
	private MasterService masterService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.blerter.grpc.service.MasterServiceGrpc.MasterServiceImplBase#postHealth(
	 * com.blerter.model.Health, io.grpc.stub.StreamObserver)
	 */
	public void postHealth(InternalRequest request, io.grpc.stub.StreamObserver<Response> responseObserver) {
		String prefix = "postHealth() ";
		Response.Builder responseBuilder = null;
		try {

			Health health = (Health) utilsFactory.deserialize(request.getData());
			responseBuilder = masterService.postHealth(health);

		} catch (Exception exc) {
			responseBuilder = Response.newBuilder();
			responseBuilder.setResponseCode(Status.InternalServerError.value());
			responseBuilder.setInfo(exc.getMessage());
		}
		Response response = responseBuilder.build();
		responseObserver.onNext(response);
		responseObserver.onCompleted();
		logger.info(prefix + " {service-grpc} completed");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.blerter.grpc.service.MasterServiceGrpc.MasterServiceImplBase#putHealth(
	 * com.blerter.model.Health, io.grpc.stub.StreamObserver)
	 */
	public void putHealth(InternalRequest request, io.grpc.stub.StreamObserver<Response> responseObserver) {
		String prefix = "putHealth() ";

		Response.Builder responseBuilder = null;
		try {

			Health health = (Health) utilsFactory.deserialize(request.getData());
			responseBuilder = masterService.putHealth(health);
		} catch (Exception exc) {
			responseBuilder = Response.newBuilder();
			responseBuilder.setResponseCode(Status.InternalServerError.value());
			responseBuilder.setInfo(exc.getMessage());
		}
		Response response = responseBuilder.build();
		responseObserver.onNext(response);
		responseObserver.onCompleted();
		logger.info(prefix + " {service-grpc} completed");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.blerter.grpc.service.MasterServiceGrpc.MasterServiceImplBase#deleteHealth
	 * (com.blerter.model.Id, io.grpc.stub.StreamObserver)
	 */
	public void deleteHealth(Id request, io.grpc.stub.StreamObserver<Response> responseObserver) {
		String prefix = "deleteHealth() ";

		Response.Builder responseBuilder = null;
		try {

			Long id = request.getId();
			responseBuilder = masterService.deleteHealth(id);
		} catch (Exception exc) {
			responseBuilder = Response.newBuilder();
			responseBuilder.setResponseCode(Status.InternalServerError.value());
			responseBuilder.setInfo(exc.getMessage());
		}
		Response response = responseBuilder.build();
		responseObserver.onNext(response);
		responseObserver.onCompleted();
		logger.info(prefix + " {service-grpc} completed");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.blerter.grpc.service.MasterServiceGrpc.MasterServiceImplBase#getHealth(
	 * com.blerter.model.Id, io.grpc.stub.StreamObserver)
	 */
	public void getHealth(Id request, io.grpc.stub.StreamObserver<Response> responseObserver) {
		String prefix = "getHealth() ";

		Response.Builder responseBuilder = null;
		try {

			Long id = request.getId();
			responseBuilder = masterService.getHealth(id);
		} catch (Exception exc) {
			responseBuilder = Response.newBuilder();
			responseBuilder.setResponseCode(Status.InternalServerError.value());
			responseBuilder.setInfo(exc.getMessage());
		}
		Response response = responseBuilder.build();
		responseObserver.onNext(response);
		responseObserver.onCompleted();
		logger.info(prefix + " {service-grpc} completed");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.blerter.grpc.service.MasterServiceGrpc.MasterServiceImplBase#ping(com.
	 * blerter.model.Empty, io.grpc.stub.StreamObserver)
	 */
	public void ping(com.blerter.model.Empty request,
			io.grpc.stub.StreamObserver<com.blerter.model.Response> responseObserver) {
		Response.Builder responseBuilder = Response.newBuilder();
		responseBuilder.setResponseCode(Status.Ok.value());
		Response response = responseBuilder.build();
		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}
}


