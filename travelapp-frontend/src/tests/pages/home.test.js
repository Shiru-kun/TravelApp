import { render, screen } from "@testing-library/react";
import { BrowserRouter } from 'react-router-dom';
import { QueryClient, QueryClientProvider } from 'react-query';
import Home from "../../pages/home";
import userEvent from "@testing-library/user-event";
const mockNavigate = jest.fn();
jest.mock('react-router',()=>({
    ...jest.requireActual('react-router'),
    useNavigate:()=>mockNavigate
}
));
const MockHome = () => {
    const queryClient = new QueryClient();
    return (<QueryClientProvider client={queryClient}>
        <BrowserRouter>
            <Home />
        </BrowserRouter>
    </QueryClientProvider>);
};
beforeEach(()=>{
    render(
        <MockHome/>
    );

});
describe("Lets test home",()=>{
  it("it should render input",()=>{
    const searchForCityKey = "SearchForCity";
    expect(screen.findByPlaceholderText(searchForCityKey));
    expect(screen.getByPlaceholderText(searchForCityKey)).toBeEnabled();

  })
  it("it should render tabs for content weather, exchange rate, gpd and population data",()=>{
   
    expect(screen.findByText("Weather"));
    expect(screen.findByText("ExchangeRate"));
    expect(screen.findByText("PopulationAndGpd"));


  })
  it("user can type search input",()=>{
    const searchForCityKey = "SearchForCity";

    const inputSearch = screen.getByPlaceholderText(searchForCityKey);
    userEvent.clear(inputSearch);
    userEvent.type(inputSearch,"Pemba");
    expect(inputSearch).toHaveAttribute('value', "Pemba");
  })
})