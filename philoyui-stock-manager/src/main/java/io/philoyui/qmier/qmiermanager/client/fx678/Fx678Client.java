package io.philoyui.qmier.qmiermanager.client.fx678;

public interface Fx678Client {

    <T extends Fx678Response> T execute(Fx678Request<T> request);


}
