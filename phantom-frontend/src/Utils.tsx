export const buildEventSource = (url: string) => {
  return new EventSource(url, {});
};