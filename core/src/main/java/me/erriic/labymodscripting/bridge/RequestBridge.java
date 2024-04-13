package me.erriic.labymodscripting.bridge;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.labymod.api.util.io.web.request.types.FileRequest;
import net.labymod.api.util.io.web.request.types.GsonRequest;
import net.labymod.api.util.io.web.request.types.InputStreamRequest;
import net.labymod.api.util.io.web.request.types.StringRequest;
import net.labymod.api.util.io.web.request.types.TypeTokenGsonRequest;
import org.jetbrains.annotations.NotNull;

public class RequestBridge {

  public <T> GsonRequest<T> ofGson(@NotNull Class<T> type) {
    return GsonRequest.of(type);
  }

  public <T> GsonRequest<T> ofGson(@NotNull Class<T> type, @NotNull Gson gson) {
    return GsonRequest.of(type, gson);
  }

  public <T> GsonRequest<T> ofGson(@NotNull Class<T> type, @NotNull Supplier<Gson> gsonSupplier) {
    return GsonRequest.of(type, gsonSupplier);
  }

  public <T> TypeTokenGsonRequest<T> ofGson(@NotNull TypeToken<T> typeToken) {
    return TypeTokenGsonRequest.of(typeToken);
  }

  public <T> TypeTokenGsonRequest<T> ofGson(@NotNull TypeToken<T> typeToken, @NotNull Gson gson) {
    return TypeTokenGsonRequest.of(typeToken, gson);
  }

  public <T> TypeTokenGsonRequest<T> ofGson(
      @NotNull TypeToken<T> typeToken,
      @NotNull Supplier<Gson> gsonSupplier
  ) {
    return TypeTokenGsonRequest.of(typeToken, gsonSupplier);
  }

  public <T> TypeTokenGsonRequest<List<T>> ofGsonList(
      @NotNull Class<T> type
  ) {
    return TypeTokenGsonRequest.ofList(type);
  }

  public <T> TypeTokenGsonRequest<List<T>> ofGsonList(
      @NotNull Class<T> type,
      @NotNull Supplier<Gson> gsonSupplier
  ) {
    return TypeTokenGsonRequest.ofList(type, gsonSupplier);
  }

  public InputStreamRequest ofInputStream() {
    return InputStreamRequest.create();
  }

  public StringRequest ofString() {
    return StringRequest.create();
  }

  public FileRequest ofFile(@NotNull Path path) {
    return FileRequest.of(path);
  }

  public FileRequest ofFile(@NotNull Path path, Consumer<Double> percentageConsumer) {
    return FileRequest.of(path, percentageConsumer);
  }

}
