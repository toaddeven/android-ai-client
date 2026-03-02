package com.openclaw.android.data.gateway;

import com.google.gson.Gson;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class GatewayConnection_Factory implements Factory<GatewayConnection> {
  private final Provider<Gson> gsonProvider;

  public GatewayConnection_Factory(Provider<Gson> gsonProvider) {
    this.gsonProvider = gsonProvider;
  }

  @Override
  public GatewayConnection get() {
    return newInstance(gsonProvider.get());
  }

  public static GatewayConnection_Factory create(Provider<Gson> gsonProvider) {
    return new GatewayConnection_Factory(gsonProvider);
  }

  public static GatewayConnection newInstance(Gson gson) {
    return new GatewayConnection(gson);
  }
}
