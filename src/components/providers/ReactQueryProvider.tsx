import queryClient from '@lib/reactQuery';
import { QueryClientProvider } from '@tanstack/react-query';

const ReactQueryProvider: React.FC<Readonly<React.PropsWithChildren>> = ({
  children,
}) => (
  <QueryClientProvider client={queryClient}>{children}</QueryClientProvider>
);

export default ReactQueryProvider;
